package com.tutrit.httpclient.gateway.CustomerGateway;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tutrit.bean.Customer;
import com.tutrit.gateway.CustomerGateway;
import com.tutrit.httpclient.gateway.config.HttpClientConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;

@Qualifier
@Component
public class HttpCustomerGateway implements CustomerGateway {
    private final ObjectMapper objectMapper;
    private final HttpClient httpClient;
    private final HttpClientConfig config;

    @Autowired
    public HttpCustomerGateway(
            final ObjectMapper objectMapper,
            final HttpClient httpClient,
            final HttpClientConfig config) {
        this.objectMapper = objectMapper;
        this.httpClient = httpClient;
        this.config = config;
    }


    @Override
    public Customer saveCustomer(Customer customer) {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("%s/%s".formatted(config.getRestApiUrl(), customer.customerId())))
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
                .uri(URI.create("%s/%s".formatted(config.getRestApiUrl(), customerId)))
                .build();

        HttpResponse<String> response = performGet(request);
        Customer customer = mapJsonStringToCustomer(response);
        return Optional.ofNullable(customer);
    }

    private HttpResponse<String> performGet(final HttpRequest request) {
        try {
            return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Can't perform get", e);
        }
    }

    private Customer mapJsonStringToCustomer(final HttpResponse<String> response) {
        try {
            return objectMapper.readValue(response.body(), Customer.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException("Can't read response get", e);
        }
    }

    private HttpRequest.BodyPublisher createCustomerBodyPublisher(Customer customer) {
        return HttpRequest.BodyPublishers.ofString(
                (String.format(
                        "{\"customerId\":\"%s\"\"name\":\"%s\",\"city\":\"%s\",\"phoneNumber\":\"%s\",\"email\":\"%s\"}",
                        customer.customerId(), customer.name(), customer.city(), customer.phoneNumber(), customer.email())));
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


