package com.tutrit.webclient.gateway;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tutrit.webclient.bean.User;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Component
public class EngineerGateway {
    HttpClient httpClient;

    public User getEngineer() throws Exception {
        HttpClient httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("http://localhost:8080"))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response);

        ObjectMapper objectMapper = new ObjectMapper();
        User user = objectMapper.readValue(response.body(), User.class);

        return user;
    }

    public void postEngineer(User user) throws Exception {
        HttpClient httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/engineers"))
                .POST(HttpRequest.BodyPublishers
                        .ofString(String.format(
                                "{\"name\":\"%s\"}",
                                user.userName())))
                .header("Content-Type", "application/json")
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        // print status code
        System.out.println(response.statusCode());
    }
}
