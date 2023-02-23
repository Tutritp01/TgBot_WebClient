package com.tutrit.gateway;

import com.tutrit.bean.Car;

import java.util.Optional;

public interface CarGateway {
    Optional<Car> saveCar(Car car);
    Optional<Car> findCarById(String id);
}
