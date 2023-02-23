package com.tutrit.controller.gateway;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tutrit.bean.Car;
import com.tutrit.gateway.CarGateway;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Optional;

import static java.time.temporal.ChronoUnit.SECONDS;

public class CarGatewayHttp implements CarGateway {

    @Override
    public Optional<Car> saveCar(Car car) {
        HttpClient httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(String.format("http://localhost:8080/cars/%s", car.carId())))
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
                .POST(HttpRequest.BodyPublishers.ofString("{}"))
                .build();

        try {
            httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Car> findCarById(String id) {
        HttpClient httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .timeout(Duration.of(5, SECONDS))
                .GET()
                .uri(URI.create(String.format("http://localhost:8080/cars/%s", id)))
                .build();

        HttpResponse<String> response = null;

        Car car;
        try {
            response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            ObjectMapper objectMapper = new ObjectMapper();
            car = objectMapper.readValue(response.body(), Car.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        System.out.println("Exception");
        return Optional.of(car);
    }
}
