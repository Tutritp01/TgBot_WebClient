package com.tutrit.controller.gateway;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tutrit.bean.Car;
import com.tutrit.gateway.CarGateway;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Objects;
import java.util.Optional;

@Component
public class CarGatewayHttp implements CarGateway {
    private static final String REQUEST_URI = "http://3.124.0.23:8082/cars/";

    private static final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .build();

    @Override
    public Car saveCar(Car car) {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(REQUEST_URI))
                .POST(HttpRequest.BodyPublishers
                        .ofString(String.format(
                                "{\"carId\": \"%s\", \"owner\": \"%s\", \"vin\": \"%s\", " +
                                        "\"plateNumber\": \"%s\", \"brand\": \"%s\", \"model\": \"%s\", " +
                                        "\"generation\": \"%s\", \"modification\": \"%s\", \"engine\": \"%s\", " +
                                        "\"year\": %s }",
                                car.carId(),
                                car.owner(),
                                car.vin(),
                                car.plateNumber(),
                                car.brand(),
                                car.model(),
                                car.generation(),
                                car.modification(),
                                car.engine(),
                                car.year())))
                .header("Content-Type", "application/json")
                .build();

        try {
            httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return car;
    }

    @Override
    public Optional<Car> findCarById(String id) {

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(REQUEST_URI + id))
                .build();

        HttpResponse<String> response = null;
        try {
            response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        Car car = null;
        try {
            if (Objects.requireNonNull(response).request().bodyPublisher().isPresent()) {
                ObjectMapper objectMapper = new ObjectMapper();
                car = objectMapper.readValue(response.body(), Car.class);
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return Optional.ofNullable(car);
    }

    @Override
    public boolean deleteCarById(String id) {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(REQUEST_URI + id))
                .POST(HttpRequest.BodyPublishers.noBody())
                .build();

        try {
            httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return false;
    }
}
