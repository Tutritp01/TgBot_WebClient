package com.tutrit.httpclient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tutrit.bean.User;
import com.tutrit.gateway.UserGateway;
import com.tutrit.httpclient.config.ConfigProvider;
import com.tutrit.httpclient.config.HttpClientConfig;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;

@Component
public class HttpUserGateway implements UserGateway {

    private final ObjectMapper objectMapper;
    private final ConfigProvider config;
    private final HttpClient httpClient;

    public HttpUserGateway(

            final ConfigProvider config,
            final HttpClient httpClient,
            final ObjectMapper objectMapper
    ) {
        this.objectMapper = objectMapper;
        this.config = config;
        this.httpClient = httpClient;
    }


    @Override
    public Optional<User> findUserById(String userId) {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("%s/%s".formatted(config.getUrl(), userId)))
                .build();
        HttpResponse<String> response = getStringHttpResponse(request);
        User user = mapJsonStringToUser(response);
        return Optional.ofNullable(user);
    }

    private User mapJsonStringToUser(HttpResponse<String> response) {
        try {
            return objectMapper.readValue(response.body(), User.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Can't read mapJsonStringToUser", e);// TODO: 07.03.2023
        }
    }

    private HttpResponse<String> getStringHttpResponse(HttpRequest request) {
        try {
            return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Can't read getStringHttpResponse", e);// TODO: 07.03.2023
        }
    }

    private HttpRequest.BodyPublisher createUserBodyPublisher(User user) {
        return HttpRequest.BodyPublishers.ofString(
                """
                        { "userId":"%s", "name":"%s", "phoneNumber":"%s"}
                        """.formatted(
                        user.userId(), user.name(), user.phoneNumber()
                ));
    }

    @Override
    public User saveUser(User user) {
        HttpRequest request = HttpRequest.newBuilder()
                .POST(createUserBodyPublisher(user))
                .uri(URI.create("%s/%s".formatted(config.getUrl(), user.userId())))
                .header("Content-Type", "application/json")
                .build();
        errorWhileSaveUser(getStringHttpResponse(request));
        return user;
    }

    private void errorWhileSaveUser(HttpResponse<String> response) {
        if (response.statusCode() < 200 || response.statusCode() > 299) {
            throw new RuntimeException("Can't to save user: " + response.body());
        }
    }

    @Override
    public boolean deleteUserById(String userId) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("%s/%s".formatted(config.getUrl(), userId)))
                .DELETE()
                .build();
        HttpResponse<String> response = getStringHttpResponse(request);
        return response != null && response.request().bodyPublisher().isPresent();
    }
}
