package com.tutrit.httpclient.controller;

import com.tutrit.httpclient.config.WebClientUrlConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConfigController {

    @Autowired
    WebClientUrlConfig endpointConfig;


}
