package com.tutrit.gateway;

import com.tutrit.bean.Car;

public interface CarGateway {
    Car findCarById(String id);
    Car saveCar(Car car);
    boolean deleteCar(String id);
}
