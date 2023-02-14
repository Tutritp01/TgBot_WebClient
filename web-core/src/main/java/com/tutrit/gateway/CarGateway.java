package com.tutrit.gateway;

import com.tutrit.bean.Car;

import java.util.List;

public interface CarGateway {
    Car saveCar(Car car);
    Iterable<Car> findCarById(String id);
    List<Car> findAllCars();
    Car update(String id);
    boolean deleteCar(Car car);
    boolean deleteCarById(String id);
}
