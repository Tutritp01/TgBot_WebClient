package com.tutrit.httpclient.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebClientUrlConfig {

    @Value("${endpoint.web-client}")
    private String webClientUrl;

    public String getUrl(){
        return webClientUrl;
    }

}
