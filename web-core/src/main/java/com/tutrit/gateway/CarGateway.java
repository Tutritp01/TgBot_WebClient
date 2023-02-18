package com.tutrit.gateway;

import com.tutrit.bean.Car;

public interface CarGateway {
    Car saveCar(Car car);
    Car findCarById(String id);
    Iterable<Car> findAllCars();
    Car update(String id);
    boolean deleteCar(Car car);
    boolean deleteCarById(String id);
}
