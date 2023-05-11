package com.tutrit.httpclient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tutrit.bean.Engineer;
import com.tutrit.config.ConfigProvider;
import com.tutrit.exception.NotImplementedException;
import com.tutrit.gateway.EngineerGateway;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;

@Component
public class HttpEngineerGateway implements EngineerGateway {
    private static final String CONTENT_TYPE = "application/json";
    private static final String PATH = "engineers";
    private final ObjectMapper objectMapper;
    private final ConfigProvider config;
    private final HttpClient httpClient;

    public HttpEngineerGateway(
            final ConfigProvider config,
            final HttpClient httpClient,
            final ObjectMapper objectMapper
    ) {
        this.objectMapper = objectMapper;
        this.config = config;
        this.httpClient = httpClient;
    }
    @Override
    public Engineer saveEngineer(Engineer engineer) {


        String url = "%s/%s/%s".formatted(config.getUrl(), PATH, engineer.engineerId());
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", CONTENT_TYPE)
                .POST(createEngineerBodyPublisher(engineer))
                .build();

        sendHttpRequest(request);
        return engineer;
    }


    private HttpRequest.BodyPublisher createEngineerBodyPublisher(Engineer engineer) {
        try {
            String string = objectMapper.writeValueAsString(engineer);
            return HttpRequest.BodyPublishers.ofString(string);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error for serialize engineer object to JSON ", e);
        }
    }

    @Override
    public Optional<Engineer> findEngineerById(String engineerId) {
        validateEngineerId(engineerId);

        String url = "%s/%s/%s".formatted(config.getUrl(), PATH, engineerId);
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(url))
                .build();

        HttpResponse<String> response = sendHttpRequest(request);
        checkResponse(response);

        Engineer engineer = mapJsonStringToEngineer(response);

        return Optional.ofNullable(engineer);
    }

    private void validateEngineerId(String engineerId) {
        if (engineerId == null || engineerId.isBlank()) {
            throw new IllegalArgumentException("Engineer ID can't be null or empty");
        }
    }

    private Engineer mapJsonStringToEngineer(HttpResponse<String> response) {
        try {
            return objectMapper.readValue(response.body(), Engineer.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Can't read mapJsonStringToEngineer", e);
        }
    }

    private HttpResponse<String> sendHttpRequest(final HttpRequest request) {
        try {
            return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Can't read getStringHttpResponse", e);
        }
    }

    private void checkResponse(HttpResponse<String> response) {
        if (response.statusCode() < 200 || response.statusCode() > 299) {
            throw new RuntimeException("Engineer not saved: " + response.body());
        }
    }

    @Override
    public boolean deleteEngineerById(String engineerId){
        throw new NotImplementedException("This method is not implemented");
    }
}
