package com.tutrit.httpclient.gateway.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.http.HttpClient;

import static java.net.http.HttpClient.Version.HTTP_2;

@Configuration
public class HttpClientConfig {
    @Value("specify the variable")
    private String restApiUrl;

    String getRestApiUrl() {
        return restApiUrl;
    }

    @Bean
    public HttpClient httpClient() {
        return HttpClient.newBuilder().version(HTTP_2).build();
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}
