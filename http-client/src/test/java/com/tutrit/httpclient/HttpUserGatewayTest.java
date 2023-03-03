package com.tutrit.httpclient;

import com.tutrit.bean.User;
import com.tutrit.httpclient.config.SpringContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = SpringContext.SpringConfig.class)
class HttpUserGatewayTest {

    @Value("${endpoint.web-client}")
    private String webClientUrl;

    @Autowired
    HttpUserGateway httpUserGateway;

    @Test
    void webClientUrl() {
        assertEquals("http://localhost:8080", webClientUrl);
    }

    @Test
    void findUserById() {
        assertInstanceOf(Optional.class, httpUserGateway.findUserById("666"));
    }

    @Test
    void saveUser() {
        assertEquals(createUser(), httpUserGateway.saveUser(createUser()));
    }

    @Test
    void deleteUser() {
        assertFalse( httpUserGateway.deleteUserById("666"));
    }

    private User createUser() {
        return new User("666", "AliceCooper", "+375291234");
    }

}
