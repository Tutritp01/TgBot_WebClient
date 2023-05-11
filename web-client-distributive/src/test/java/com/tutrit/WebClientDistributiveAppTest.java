package com.tutrit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tutrit.distributive.WebClientDistributiveApp;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@AutoConfigureMockMvc
@SpringBootTest(classes = WebClientDistributiveApp.class)
class WebClientDistributiveAppTest {

    @Autowired
    ApplicationContext ctx;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    MockMvc mockMvc;

    @Test
    void contextLoads() {
        assertNotNull(ctx);
        assertNotNull(objectMapper);
        assertNotNull(mockMvc);
    }
}

