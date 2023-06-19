package com.tutrit.httpclient;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tutrit.bean.User;
import com.tutrit.config.ConfigProvider;
import com.tutrit.config.SpringContext;
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
class UserGatewayHttpTest {
    private static final String URL_REST_API = "http://localhost:8080";
    private static final String PATH = "users";
    public static final User USER = new User("666", "AliceCooper", "+375291234");
    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    UserGatewayHttp httpUserGateway;

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
    void findUserById() throws IOException, InterruptedException {
        when(httpClient.send(makeRequest(), HttpResponse.BodyHandlers.ofString())).thenReturn(httpResponse);
        when(httpResponse.body()).thenReturn(objectMapper.writeValueAsString(USER));
        when(httpResponse.statusCode()).thenReturn(HttpStatus.OK.value());

        Optional<User> actualUser = httpUserGateway.findUserById(USER.userId());

        assertTrue(actualUser.isPresent());
        assertEquals(USER,actualUser.get());
    }
    @Test
    void saveUser() throws IOException, InterruptedException {
        when(httpClient.send(any(HttpRequest.class), eq(HttpResponse.BodyHandlers.ofString()))).thenReturn(httpResponse);
        when(httpResponse.body()).thenReturn(objectMapper.writeValueAsString(USER));
        when(httpResponse.statusCode()).thenReturn(HttpStatus.CREATED.value());
        User saveUser = httpUserGateway.saveUser(USER);
        assertEquals(USER, saveUser);

    }
    HttpRequest makeRequest() {
        String url = "%s/%s/%s".formatted(URL_REST_API,PATH, USER.userId());
        return HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(url))
                .build();
    }

}
