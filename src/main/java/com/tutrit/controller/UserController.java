package com.tutrit.controller;

import com.tutrit.gateway.UserGateway;
import org.springframework.stereotype.Controller;

@Controller
public class UserController {
    private final UserGateway userGateway;

    public UserController(final UserGateway userGateway) {
        this.userGateway = userGateway;
    }
}
