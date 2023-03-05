package com.tutrit.controller.gateway;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tutrit.bean.Car;
import com.tutrit.config.ConfigProvider;
import com.tutrit.gateway.CarGateway;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;

@Component
public class CarGatewayHttp implements CarGateway {
    private final ObjectMapper objectMapper;
    private final HttpClient httpClient;
    private final ConfigProvider configProvider;

    public CarGatewayHttp(ObjectMapper objectMapper, HttpClient httpClient, ConfigProvider configProvider) {
        this.objectMapper = objectMapper;
        this.httpClient = httpClient;
        this.configProvider = configProvider;
    }

    @Override
    public Car saveCar(Car car) {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(makeUri(car.carId())))
                .POST(HttpRequest.BodyPublishers.ofString(setBody(car)))
                .header("Content-Type", "application/json")
                .build();

        performGet(request);
        return car;
    }


    @Override
    public Optional<Car> findCarById(String id) {

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(makeUri(id)))
                .build();

        HttpResponse<String> response = performGet(request);
        Car car = mapJsonStringToUser(response);
        return Optional.ofNullable(car);
    }

    @Override
    public boolean deleteCarById(String id) {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(makeUri(id)))
                .POST(HttpRequest.BodyPublishers.noBody())
                .build();

        performGet(request);
        return false;
    }

    private String makeUri(String id) {
        return "%s/%s/%s".formatted(configProvider.getUrl(), "cars", id);
    }

    private String setBody(Car car) {
        try {
            return objectMapper.writeValueAsString(car);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Can't perform get", e);
        }
    }

    private HttpResponse<String> performGet(final HttpRequest request) {
        try {
            return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Can't perform get", e);
        }
    }

    private Car mapJsonStringToUser(final HttpResponse<String> response) {
        try {
            return objectMapper.readValue(response.body(), Car.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException("Can't read response get", e);
        }
    }
}
