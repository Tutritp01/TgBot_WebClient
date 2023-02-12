package com.tutrit.controller;

import com.tutrit.gateway.CarGateway;
import org.springframework.stereotype.Controller;

@Controller
public class CarController {

    private final CarGateway carGateway;

    public CarController(final CarGateway carGateway) {
        this.carGateway = carGateway;
    }
}
