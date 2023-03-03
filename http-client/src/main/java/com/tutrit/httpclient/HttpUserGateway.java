package com.tutrit.httpclient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tutrit.bean.User;
import com.tutrit.gateway.UserGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Optional;

import static java.net.http.HttpClient.Version.HTTP_2;
import static java.time.temporal.ChronoUnit.SECONDS;

@Qualifier
@Component
public class HttpUserGateway implements UserGateway {
    @Autowired
    private ObjectMapper objectMapper;
    private String webClientUrl;


    @Autowired
    public HttpUserGateway(@Value("${endpoint.web-client}") String webClientUrl) {
        this.webClientUrl = webClientUrl + "/users";
    }

    private static final HttpClient httpClient = HttpClient.newBuilder().version(HTTP_2).build();


    @Override
    public Optional<User> findUserById(String userId) {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(webClientUrl + "/" + userId))
                .timeout(Duration.of(1, SECONDS))
                .build();
        HttpResponse<String> response = null;
        try {
            response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        User user = null;
        if (response != null && response.request().bodyPublisher().isPresent()) {
            try {
                user = objectMapper.readValue(response.body(), User.class);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        return Optional.ofNullable(user);
    }

    @Override
    public User saveUser(User user) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(webClientUrl + "/" + user.userId()))
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
            System.out.println(response.statusCode());
        }
        return user;
    }


    @Override
    public boolean deleteUserById(String userId) {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(webClientUrl + "/" + userId))
                .timeout(Duration.of(1, SECONDS))
                .POST(HttpRequest.BodyPublishers.noBody())
                .build();
        try {
             httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            Thread.currentThread().interrupt();
        }
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
