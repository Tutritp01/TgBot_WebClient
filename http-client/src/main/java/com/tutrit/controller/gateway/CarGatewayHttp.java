package com.tutrit.controller.gateway;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tutrit.bean.Car;
import com.tutrit.gateway.CarGateway;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;

public class CarGatewayHttp implements CarGateway {

    @Override
    public Optional<Car> saveCar(Car car) {
        HttpClient httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080"))
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
        return Optional.empty();
    }

    @Override
    public Optional<Car> findCarById(String id) {
        HttpClient httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("http://localhost:8080"))
                .build();

        HttpResponse<String> response = null;
        try {
            response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        Car car = null;
        try {
            if (response != null) {
                ObjectMapper objectMapper = new ObjectMapper();
                car = objectMapper.readValue(response.body(), Car.class);
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return Optional.ofNullable(car);
    }
}
