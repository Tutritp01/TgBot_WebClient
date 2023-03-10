package com.tutrit.httpclient.gateway.CustomerGateway;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tutrit.bean.Customer;
import com.tutrit.config.ConfigProvider;
import com.tutrit.config.SpringContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


@SpringBootTest(classes = SpringContext.SpringConfig.class)
class HttpCustomerGatewayTest {
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    HttpCustomerGateway httpCustomerGateway;
    @MockBean
    ConfigProvider configProvider;
    @MockBean
    HttpClient httpClient;
    @MockBean
    HttpResponse httpResponse;

    @BeforeEach
    public void setUp() throws IOException, InterruptedException {
        Mockito.when(configProvider.getUrl()).thenReturn("http://localhost:8090");

    }

    @Test
    void saveCustomer() throws IOException, InterruptedException {
        when(httpClient.send(makeRequest(), HttpResponse.BodyHandlers.ofString())).thenReturn(httpResponse);
        when(httpResponse.body()).thenReturn(objectMapper.writeValueAsString(createCustomer()));
        when(httpResponse.statusCode()).thenReturn(HttpStatus.OK.value());
        Customer customer = httpCustomerGateway.saveCustomer(createCustomer());
        assertEquals(createCustomer(), customer);
    }

    @Test
    void findCustomerById() throws IOException, InterruptedException {
        when(httpClient.send(makeRequest(), HttpResponse.BodyHandlers.ofString())).thenReturn(httpResponse);
        when(httpResponse.body()).thenReturn(objectMapper.writeValueAsString(createCustomer()));
        when(httpResponse.statusCode()).thenReturn(HttpStatus.OK.value());

        Customer customer = httpCustomerGateway.findCustomerById("44").get();
        assertEquals(createCustomer(), customer);
    }

    @Test
    void deleteCustomerById() {
    }

    private Customer createCustomer() {
        return new Customer("23453434", "name", "city", "phoneNumber", "email");
    }

    HttpRequest makeRequest() {
        return HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("http://localhost:8090/customers/44"))
                .build();
    }

}
