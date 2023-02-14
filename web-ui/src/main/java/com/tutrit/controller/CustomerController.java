package com.tutrit.controller;

import com.tutrit.gateway.CustomerGateway;
import org.springframework.stereotype.Controller;

@Controller
public class CustomerController {
    private final CustomerGateway customerGateway;

    public CustomerController(final CustomerGateway customerGateway) {
        this.customerGateway = customerGateway;
    }
}