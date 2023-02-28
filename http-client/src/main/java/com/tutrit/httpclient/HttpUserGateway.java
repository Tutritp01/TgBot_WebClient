package com.tutrit.httpclient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tutrit.bean.User;
import com.tutrit.gateway.UserGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;

import static java.net.http.HttpClient.Version.HTTP_2;


@Component
public class HttpUserGateway implements UserGateway {
    @Autowired
    private ObjectMapper objectMapper;
    public static final String GENERAL_URI = "http://localhost:8080";
    private final HttpClient httpClient = HttpClient.newBuilder().version(HTTP_2).build();

    @Override
    public Optional<User> findUserById(String userId) {

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(GENERAL_URI))
                .build();

        HttpResponse<String> response = null;
        User user = null;
        try {
            response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        if (response.request().bodyPublisher().isPresent()) {
            try {
                user = objectMapper.readValue(response.body(), User.class);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
        return Optional.ofNullable(user);
    }

    @Override
    public User saveUser(User user) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(GENERAL_URI + user.userId()))
                .POST(createUserBodyPublisher(user))
                .header("Content-Type", "application/json")
                .build();
        HttpResponse<String> response = null;
        try {
            response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(response.statusCode());
        return null;
    }


    @Override
    public boolean deleteUserById(String userId) {
        return false;
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
