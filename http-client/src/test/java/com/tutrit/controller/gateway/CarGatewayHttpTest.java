package com.tutrit.controller.gateway;

import com.tutrit.bean.Car;
import com.tutrit.gateway.CarGateway;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

class CarGatewayHttpTest {

    CarGateway carGateway;

    @BeforeEach
    void setUp() {
        carGateway = new CarGatewayHttp();
    }

    @Test
    void saveCar() {
        Car result = carGateway.saveCar(makeCar());
        Assertions.assertEquals(makeCar(), result);
    }

    @Test
    void findCarById() {
        Optional<Car> result = carGateway.findCarById("12345");
        Assertions.assertInstanceOf(Optional.class, result);
    }

    @Test
    void deleteCarById() {
        Assertions.assertFalse(carGateway.deleteCarById("12345"));
    }

    private Car makeCar() {
        return new Car("12345", "John Doe", "12345678901234567", "ABC123", "Toyota", "Corolla", "XLE", "Sport", "1.8L", 2022);
    }
}
