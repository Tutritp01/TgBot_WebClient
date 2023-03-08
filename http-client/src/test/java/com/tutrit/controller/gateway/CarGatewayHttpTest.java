package com.tutrit.controller.gateway;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tutrit.bean.Car;
import com.tutrit.config.ConfigProvider;
import com.tutrit.config.SpringContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@SpringBootTest(classes = SpringContext.SpringConfig.class)
class CarGatewayHttpTest {

    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    CarGatewayHttp carGatewayHttp;
    @MockBean
    ConfigProvider webClientUrlConfig;
    @MockBean
    HttpClient httpClient;
    @MockBean
    HttpResponse httpResponse;

    @BeforeEach
    void setUp() {
        Mockito.when(webClientUrlConfig.getUrl()).thenReturn("http://localhost:8080");
    }

    @Test
    void saveCar() throws IOException, InterruptedException {
        Mockito.when(httpClient.send(makeRequest(), HttpResponse.BodyHandlers.ofString())).thenReturn(httpResponse);
        Mockito.when(httpResponse.body()).thenReturn(objectMapper.writeValueAsString(makeCar()));
        Mockito.when(httpResponse.statusCode()).thenReturn(HttpStatus.CREATED.value());

        Car result = carGatewayHttp.saveCar(makeCar());

        Assertions.assertEquals(makeCar(), result);
    }

    @Test
    void findCarById() throws IOException, InterruptedException {
        Mockito.when(httpClient.send(makeRequest(), HttpResponse.BodyHandlers.ofString())).thenReturn(httpResponse);
        Mockito.when(httpResponse.body()).thenReturn(objectMapper.writeValueAsString(makeCar()));
        Mockito.when(httpResponse.statusCode()).thenReturn(HttpStatus.OK.value());

        Car car = carGatewayHttp.findCarById("12345").get();

        Assertions.assertEquals(makeCar(), car);
    }

    HttpRequest makeRequest() {
        return HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("http://localhost:8080/cars/12345"))
                .build();
    }

    private Car makeCar() {
        return new Car("12345", "John Doe", "12345678901234567", "ABC123", "Toyota", "Corolla", "XLE", "Sport", "1.8L", 2022);
    }
}
