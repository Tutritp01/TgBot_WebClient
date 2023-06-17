package com.tutrit.httpclient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tutrit.bean.User;
import com.tutrit.config.ConfigProvider;
import com.tutrit.gateway.UserGateway;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;

@Component
public class UserGatewayHttp implements UserGateway {
    private static final String CONTENT_TYPE = "application/json";
    private static final String PATH = "users";
    private final ObjectMapper objectMapper;
    private final ConfigProvider config;
    private final HttpClient httpClient;

    public UserGatewayHttp(
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
        validateUserId(userId);

        String url = "%s/%s/%s".formatted(config.getUrl(), PATH, userId);
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(url))
                .build();

        HttpResponse<String> response = sendHttpRequest(request);
        checkResponse(response);

        User user = mapJsonStringToUser(response);

        return Optional.ofNullable(user);
    }

    private void validateUserId(String userId) {
        if (userId == null || userId.isBlank()) {
            throw new IllegalArgumentException("User ID can't be null or empty");
        }
    }

    private User mapJsonStringToUser(HttpResponse<String> response) {
        try {
            return objectMapper.readValue(response.body(), User.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Can't read mapJsonStringToUser", e);// TODO: 07.03.2023
        }
    }

    private HttpResponse<String> sendHttpRequest(final HttpRequest request) {
        try {
            return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Can't read getStringHttpResponse", e);// TODO: 07.03.2023
        }
    }

    private void checkResponse(HttpResponse<String> response) {
        if (response.statusCode() < 200 || response.statusCode() > 299) {
            throw new RuntimeException("Can't to save user: " + response.body());
        }
    }

    @Override
    public User saveUser(User user) {
        HttpResponse<String> response = null;

        if (user.userId() == null) {
            String url = "%s/%s".formatted(config.getUrl(), PATH);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Content-Type", CONTENT_TYPE)
                    .POST(createUserBodyPublisher(user))
                    .build();

            response = sendHttpRequest(request);
        } else {
            String url = "%s/%s/%s".formatted(config.getUrl(), PATH, user.userId());
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Content-Type", CONTENT_TYPE)
                    .POST(createUserBodyPublisher(user))
                    .build();

            response = sendHttpRequest(request);
        }

        checkResponse(response);

        user = mapJsonStringToUser(response);

        return user;
    }

    private HttpRequest.BodyPublisher createUserBodyPublisher(User user) {
        try {
            String string = objectMapper.writeValueAsString(user);
            return HttpRequest.BodyPublishers.ofString(string);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error for serialize user object to JSON ", e);
        }
    }

    @Override
    public boolean deleteUserById(String userId) {
        return true;
    }
}
