package com.tutrit.gateway;

import com.tutrit.bean.Car;
import org.springframework.stereotype.Component;

import java.net.http.HttpClient;

@Component
public interface CarGateway {
    HttpClient httpClient = null;

    Car getCar();
    String saveCar();
    String updateCar();
    String changeCar();
    boolean deleteCar();
}
