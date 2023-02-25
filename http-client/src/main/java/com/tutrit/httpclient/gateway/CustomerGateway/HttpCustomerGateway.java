package com.tutrit.httpclient.gateway.CustomerGateway;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tutrit.bean.Customer;
import com.tutrit.gateway.CustomerGateway;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;

@Component
public class HttpCustomerGateway implements CustomerGateway {
    @Override
    public Customer saveCustomer(Customer customer){
        HttpClient httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/customersData")) //endpoint to transfer data
                .POST(HttpRequest.BodyPublishers
                        .ofString(String.format(
                                "{\"customerId\":\"%s\"\"name\":\"%s\",\"city\":\"%s\",\"phoneNumber\":\"%s\",\"email\":\"%s\"}",
                                customer.customerId(),customer.name(),customer.city(),customer.phoneNumber(),customer.email())))
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
    public Optional<Customer> findCustomerById(String customerId){
        HttpClient httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("http://localhost:8080/customersDataBase")) //endpoint to find customer
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
    public Optional<Customer> updateCustomer(String customerId) {
        return Optional.empty();
    }


}


