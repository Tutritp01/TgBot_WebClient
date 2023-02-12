package com.tutrit.gateway;

import com.tutrit.bean.Car;
import org.springframework.stereotype.Component;

@Component
public interface CarGateway {
    Car getCar();
    String saveCar();
    String updateCar();
    String changeCar();
    boolean deleteCar();
}
