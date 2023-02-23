package com.tutrit.gateway;

import com.tutrit.bean.Car;

import java.util.Optional;

public interface CarGateway {
    Optional<Car> saveCar(Car car);
    Optional<Car> findCarById(String id);
    Iterable<Car> findAllCars();
    Optional<Car> update(String id);
    boolean deleteCar(Car car);
    boolean deleteCarById(String id);
}
