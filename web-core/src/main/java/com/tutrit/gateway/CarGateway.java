package com.tutrit.gateway;

import com.tutrit.bean.Car;

import java.util.Optional;

public interface CarGateway {
    Car saveCar(Car car);

    Optional<Car> findCarById(String id);

    boolean deleteCarById(String id);
}
