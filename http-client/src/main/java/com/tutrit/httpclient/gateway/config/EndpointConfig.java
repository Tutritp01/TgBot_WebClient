package com.tutrit.httpclient.gateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;


@Configuration()
public class EndpointConfig {
    @Value("${endpoint.rest-api}")
    public String restApiUrl;

    public String getRestApiUrl() {
        return restApiUrl;
    }
}
