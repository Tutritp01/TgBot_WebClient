package com.tutrit.httpclient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tutrit.bean.User;
import com.tutrit.httpclient.config.ConfigProvider;
import com.tutrit.httpclient.config.SpringContext;
import com.tutrit.httpclient.config.WebClientUrlConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

@SpringBootTest(classes = SpringContext.SpringConfig.class)
class HttpUserGatewayTest {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    HttpUserGateway httpUserGateway;
    @MockBean
    ConfigProvider webClientUrlConfig;
    @MockBean
    HttpClient httpClient;
    @MockBean
    HttpResponse httpResponse;

    @BeforeEach
    public void setUp() throws IOException, InterruptedException {
        Mockito.when(webClientUrlConfig.getUrl()).thenReturn("http://localhost");
    }

    @Test
    void findUserById() throws IOException, InterruptedException {
        Mockito.when(httpClient.send(makeRequest(), HttpResponse.BodyHandlers.ofString()))
                .thenReturn(httpResponse);
        Mockito.when(httpResponse.body()).thenReturn(objectMapper.writeValueAsString(createUser()));
        Mockito.when(httpResponse.statusCode()).thenReturn(HttpStatus.OK.value());

        User user = httpUserGateway.findUserById("666").get();
        assertEquals(createUser(), user);
    }

    @Test
    void saveUser() {
        assertEquals(createUser(), httpUserGateway.saveUser(createUser()));
    }

    @Test
    void deleteUser() {
        assertFalse(httpUserGateway.deleteUserById("666"));
    }

    private User createUser() {
        return new User("666", "AliceCooper", "+375291234");
    }

    HttpRequest makeRequest() {
        return HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("http://localhost/666"))
                .build();
    }

}
