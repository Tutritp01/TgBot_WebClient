package com.tutrit.httpclient.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.http.HttpClient;

import static java.net.http.HttpClient.Version.HTTP_2;

@Configuration
public class HttpClientConfig {
    private final String url;

    public HttpClientConfig(@Value("${url.rest-api:http://localhost:8082}") final String url) {
        this.url = url;
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public HttpClient httpClient() {
        return HttpClient.newBuilder().version(HTTP_2).build();
    }

    public String getUrl() {
        return url;
    }

}
