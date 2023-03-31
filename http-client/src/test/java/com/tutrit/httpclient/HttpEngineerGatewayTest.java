package com.tutrit.httpclient;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tutrit.bean.Engineer;
import com.tutrit.config.ConfigProvider;
import com.tutrit.config.SpringContext;
import com.tutrit.sto.httpclient.HttpEngineerGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = SpringContext.SpringConfig.class)
class HttpEngineerGatewayTest {
    private static final String URL_REST_API = "http://localhost:8080";
    private static final String PATH = "engineers";
    public static final Engineer ENGINEER = new Engineer("777", "Artemka", "Programmer", "developer", "first", "MaxCourses", 3, 8);
    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    HttpEngineerGateway httpEngineerGateway;

    @MockBean
    ConfigProvider config;

    @MockBean
    HttpClient httpClient;

    @MockBean
    HttpResponse<String> httpResponse;

    @BeforeEach
    public void setUp() {
        when(config.getUrl()).thenReturn(URL_REST_API);
    }

    @Test
    void saveEngineer() throws IOException, InterruptedException{
        when(httpClient.send(any(HttpRequest.class), eq(HttpResponse.BodyHandlers.ofString()))).thenReturn(httpResponse);
        when(httpResponse.body()).thenReturn(objectMapper.writeValueAsString(ENGINEER));
        when(httpResponse.statusCode()).thenReturn(HttpStatus.CREATED.value());
        Engineer saveEngineer = httpEngineerGateway.saveEngineer(ENGINEER);
        assertEquals(ENGINEER, saveEngineer);
    }

    @Test
    void findEngineerById() throws IOException, InterruptedException{
        when(httpClient.send(makeRequest(), HttpResponse.BodyHandlers.ofString())).thenReturn(httpResponse);
        when(httpResponse.body()).thenReturn(objectMapper.writeValueAsString(ENGINEER));
        when(httpResponse.statusCode()).thenReturn(HttpStatus.OK.value());

        Optional<Engineer> actualEngineer = httpEngineerGateway.findEngineerById(ENGINEER.engineerId());

        assertTrue(actualEngineer.isPresent());
        assertEquals(ENGINEER,actualEngineer.get());
    }

    HttpRequest makeRequest() {
        String url = "%s/%s/%s".formatted(URL_REST_API,PATH, ENGINEER.engineerId());
        return HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(url))
                .build();
    }
}
