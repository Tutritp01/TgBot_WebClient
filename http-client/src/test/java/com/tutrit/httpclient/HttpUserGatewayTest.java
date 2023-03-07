package com.tutrit.httpclient;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tutrit.bean.User;
import com.tutrit.httpclient.config.ConfigProvider;
import com.tutrit.httpclient.config.HttpClientConfig;
import com.tutrit.httpclient.config.SpringContext;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = SpringContext.SpringConfig.class)
class HttpUserGatewayTest {
    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    HttpUserGateway httpUserGateway;

    @MockBean
    ConfigProvider config;

    @MockBean
    HttpClient httpClient;

    @MockBean
    HttpResponse httpResponse;


    @BeforeEach
    public void setUp() {
        when(config.getUrl()).thenReturn("http://localhost:80/users");
    }

     @Test
     void findUserById() throws IOException, InterruptedException {

         when(httpClient.send(makeRequest(), HttpResponse.BodyHandlers.ofString())).thenReturn(httpResponse);
         when(httpResponse.body()).thenReturn(objectMapper.writeValueAsString(expectedUser()));
         when(httpResponse.statusCode()).thenReturn(HttpStatus.OK.value());

         Optional<User> actualUser = httpUserGateway.findUserById("666");

         assertTrue(actualUser.isPresent());
         assertEquals(expectedUser(),actualUser.get());


     }

    HttpRequest makeRequest() {
        return HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("http://localhost:80/users/666"))
                .build();
    }

    @Test
    void saveUser() {
        assertEquals(expectedUser(), httpUserGateway.saveUser(expectedUser()));
    }

    @Test
    void deleteUser() {
        assertFalse(httpUserGateway.deleteUserById("666"));
    }

    private User expectedUser() {
        return new User("666", "AliceCooper", "+375291234");
    }

}
