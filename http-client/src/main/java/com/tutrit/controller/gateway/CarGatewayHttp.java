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
    private static final String LOCALHOST_8080 = "http://localhost:8080";

    private static final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .build();

    @Override
    public Car saveCar(Car car) {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(LOCALHOST_8080))
                .header("carId", car.carId())
                .header("owner", car.owner())
                .header("vin", car.vin())
                .header("plateNumber", car.plateNumber())
                .header("brand", car.brand())
                .header("model", car.model())
                .header("generation", car.generation())
                .header("modification", car.modification())
                .header("engine", car.engine())
                .header("year", String.valueOf(car.year()))
                .header("save", "save")
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.noBody())
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
                .uri(URI.create(LOCALHOST_8080))
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
                .uri(URI.create(LOCALHOST_8080))
                .header("carId", id)
                .header("delete", "delete")
                .header("Content-Type", "application/json")
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
