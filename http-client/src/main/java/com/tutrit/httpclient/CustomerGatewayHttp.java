package com.tutrit.httpclient;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tutrit.bean.Customer;
import com.tutrit.config.ConfigProvider;
import com.tutrit.gateway.CustomerGateway;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;

@Component
public class CustomerGatewayHttp implements CustomerGateway {
    private final ObjectMapper objectMapper;
    private final HttpClient httpClient;
    private final ConfigProvider config;

    public CustomerGatewayHttp(final ObjectMapper objectMapper,
                               final HttpClient httpClient,
                               final ConfigProvider config) {
        this.objectMapper = objectMapper;
        this.httpClient = httpClient;
        this.config = config;
    }

    @Override
    public Customer saveCustomer(Customer customer) {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("%s/%s".formatted(config.getUrl()+"/customers", customer.customerId())))
                .POST(createCustomerBodyPublisher(customer))
                .header("Content-Type", "application/json")
                .build();

        performGet(request);

        return customer;
    }

    @Override
    public Optional<Customer> findCustomerById(String customerId) {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("%s/%s".formatted(config.getUrl()+"/customers", customerId)))
                .build();
        HttpResponse<String> response = performGet(request);
        Customer customer = mapJsonStringToUser(response);
        return Optional.ofNullable(customer);
    }

    private Customer mapJsonStringToUser(final HttpResponse<String> response) {
        try {
            return objectMapper.readValue(response.body(), Customer.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException("Can't read response get", e);
        }
    }

    private HttpResponse<String> performGet(final HttpRequest request) {
        try {
            return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Can't perform get", e);
        }
    }
    private HttpRequest.BodyPublisher createCustomerBodyPublisher(Customer customer) {
        try {
            String string = objectMapper.writeValueAsString(customer);
            return HttpRequest.BodyPublishers.ofString(string);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error when creating the request body", e);
        }
    }

    @Override
    public boolean deleteCustomerById(String customerId) {
        return false;
    }

    @Override
    public Optional<Customer> updateCustomer(Customer customer) {
        return Optional.empty();
    }
}


