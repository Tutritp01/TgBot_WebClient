package com.tutrit.httpclient.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.http.HttpClient;

import static java.net.http.HttpClient.Version.HTTP_2;

@Configuration
public class WebClientUrlConfig {

    @Value("${endpoint.web-client}")
    private String webClientUrl;

    String getUrl(){
        return webClientUrl;
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
