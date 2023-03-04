package com.tutrit.httpclient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tutrit.bean.User;
import com.tutrit.gateway.UserGateway;
import com.tutrit.httpclient.config.ConfigProvider;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Optional;

import static java.time.temporal.ChronoUnit.SECONDS;

@Component
public class HttpUserGateway implements UserGateway {
    private final ObjectMapper objectMapper;
    private final HttpClient httpClient;
    private final ConfigProvider config;


    public HttpUserGateway(final ObjectMapper objectMapper,
                           final HttpClient httpClient,
                           final ConfigProvider config) {
        this.objectMapper = objectMapper;
        this.httpClient = httpClient;
        this.config = config;
    }


    @Override
    public Optional<User> findUserById(String userId) {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("%s/%s".formatted(config.getUrl(), userId)))
                .build();
        HttpResponse<String> response = performGet(request);
        User user = mapJsonStringToUser(response);
        return Optional.ofNullable(user);
    }

    private User mapJsonStringToUser(final HttpResponse<String> response) {
        try {
            return objectMapper.readValue(response.body(), User.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException("Can't read response get", e); // TODO: 3/4/23 create and process unfriendly exception
        }
    }

    private HttpResponse<String> performGet(final HttpRequest request) {
        try {
            return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Can't perform get", e); // TODO: 3/4/23 create and process unfriendly exception
        }
    }

    @Override
    public User saveUser(User user) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(config.getUrl() + "/" + user.userId()))
                .timeout(Duration.of(1, SECONDS))
                .POST(createUserBodyPublisher(user))
                .header("Content-Type", "application/json")
                .build();
        HttpResponse<String> response = null;
        try {
            response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        if (response != null && response.request().bodyPublisher().isPresent()) {
            System.err.println(response.statusCode());
        }
        return user;
    }


    @Override
    public boolean deleteUserById(String userId) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(config.getUrl() + "/" + userId))
                .timeout(Duration.of(1, SECONDS))
                .DELETE()
                .build();
        HttpResponse<String> response = null;
        try {
            response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return response != null && response.request().bodyPublisher().isPresent();
    }

    private HttpRequest.BodyPublisher createUserBodyPublisher(User user) {
        return HttpRequest.BodyPublishers.ofString(
                """
                        { "userId":"%s", "name":"%s", "phoneNumber":"%s"}
                        """.formatted(
                        user.userId(), user.name(), user.phoneNumber()
                ));
    }
}
