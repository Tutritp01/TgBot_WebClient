package com.tutrit.httpclient.gateway.CustomerGateway;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tutrit.bean.Customer;
import com.tutrit.gateway.CustomerGateway;
import com.tutrit.httpclient.gateway.config.EndpointConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;

@Qualifier
@Component
public class HttpCustomerGateway implements CustomerGateway {
    @Autowired
    private EndpointConfig endpointConfig;


    private static final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .build();

    @Override
    public Customer saveCustomer(Customer customer) {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(endpointConfig.getRestApiUrl()))
                .POST(HttpRequest.BodyPublishers
                        .ofString(String.format(
                                "{\"customerId\":\"%s\"\"name\":\"%s\",\"city\":\"%s\",\"phoneNumber\":\"%s\",\"email\":\"%s\"}",
                                customer.customerId(), customer.name(), customer.city(), customer.phoneNumber(), customer.email())))
                .header("Content-Type", "application/json")
                .build();

        HttpResponse<String> response = null;
        try {
            response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        // print status code
        System.out.println(response.statusCode());
        return customer;
    }

    @Override
    public Optional<Customer> findCustomerById(String customerId) {

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(endpointConfig.getRestApiUrl() + customerId))
                .build();

        HttpResponse<String> response = null;
        try {
            response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        Customer customer = null;
        if (response.request().bodyPublisher().isPresent()) {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                customer = objectMapper.readValue(response.body(), Customer.class);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
        return Optional.ofNullable(customer);
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


