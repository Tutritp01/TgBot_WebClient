package com.tutrit.gateway;

import com.tutrit.bean.Car;

public interface CarGateway {
    Car findCarById(String carId);
    Car saveCar(Car car);
}
