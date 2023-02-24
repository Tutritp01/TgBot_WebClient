package com.tutrit.httpclient.gateway.CustomerGateway;


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
    public Customer saveCustomer(Customer customer) throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/customers"))
                .POST(HttpRequest.BodyPublishers.noBody())

                .header("Content-Type", "application/json")
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        // print status code
        System.out.println(response.statusCode());
        return customer;
    }


    @Override
    public Optional<Customer> findCustomerById(String customerId) throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("http://localhost:8080/customers"))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        Customer customer = null;
        if (response.request().bodyPublisher().isPresent()) {
            ObjectMapper objectMapper = new ObjectMapper();
            customer = objectMapper.readValue(response.body(), Customer.class);
        }


        return Optional.ofNullable(customer);
    }

    @Override
    public boolean deleteCustomerById(String customerId) {
        return false;
    }


}


