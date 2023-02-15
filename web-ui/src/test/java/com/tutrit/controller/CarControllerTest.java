package com.tutrit.controller;

import com.tutrit.bean.Car;
import com.tutrit.gateway.CarGateway;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.GenericWebApplicationContext;

import java.util.Collections;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@AutoConfigureMockMvc
@ContextConfiguration
@SpringBootTest
class CarControllerTest {

    @MockBean
    CarGateway carGateway;
//    @Autowired
    private WebApplicationContext context = new GenericWebApplicationContext();
    @Autowired
    MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    void findCarById() throws Exception {
        when(carGateway.findCarById("id1"))
                .thenReturn(Collections.singleton(new Car("id1", "owner", "vin", "plateNumber", "brand1", "model1", "1g", "mod1", "engine1", 2001)));

        final MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/cars"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(model().attribute("car", Matchers.equalTo(makeCar())))
                .andExpect(view().name("car-form"))
                .andReturn();

        Car actualCar = (Car) Objects.requireNonNull(result.getModelAndView()).getModel().get("car");
        assertEquals(makeCar(), actualCar);
    }

    @Test
    void saveCar() {
    }

    private Car makeCar() {
        return new Car("id1", "owner", "vin", "plateNumber", "brand1", "model1", "1g", "mod1", "engine1", 2001);
    }
}