package com.tutrit.controller.gateway;

import com.tutrit.bean.Car;
import com.tutrit.config.SpringContext;
import com.tutrit.gateway.CarGateway;
import com.tutrit.webclient.controller.CarController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.net.http.HttpRequest;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@AutoConfigureMockMvc
@SpringBootTest(classes = SpringContext.SpringConfig.class)
class CarGatewayHttpTest {

    CarController carController;

    @Autowired
    CarGateway carGateway;
    @Autowired
    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        carGateway = new CarGatewayHttp();
    }

    @Test
    void saveCar() throws Exception {
//        Car car = new Car("12345", "John Doe", "12345678901234567", "ABC123", "Toyota", "Corolla", "XLE", "Sport", "1.8L", 2022);
        Car car = new Car("id1", "owner1", "vin1", "plateNumber1", "brand1", "model1", "1g", "mod1", "engine1", 2001);
        Optional<Car> result = carGateway.saveCar(car);
        assertTrue(result.isEmpty());

//        when(carGateway.saveCar(new Car("id1", "owner1", "vin1", "plateNumber1", "brand1", "model1", "1g", "mod1", "engine1", 2001))).thenReturn(makeCar());

//        mockMvc.perform(post("/cars/id1")
//                        .param("carId", "id1")
//                        .param("owner", "owner1")
//                        .param("vin", "vin1")
//                        .param("plateNumber", "plateNumber1")
//                        .param("brand", "brand1")
//                        .param("model", "model1")
//                        .param("generation", "1g")
//                        .param("modification", "mod1")
//                        .param("engine", "engine1")
//                        .param("year", "2001")
//                        .param("save", "push"))
//                .andExpect(view().name("redirect:/cars/id1"))
//                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
//                .andDo(MockMvcResultHandlers.print())
//                .andReturn();


        // Assert that the request headers are correct
//        HttpRequest request = HttpRequest.newBuilder().build();
//        assertEquals("12345", request.headers().firstValue("carId").get());
//        assertEquals("John Doe", request.headers().firstValue("owner").get());
//        assertEquals("12345678901234567", request.headers().firstValue("vin").get());
//        assertEquals("ABC123", request.headers().firstValue("plateNumber").get());
//        assertEquals("Toyota", request.headers().firstValue("brand").get());
//        assertEquals("Corolla", request.headers().firstValue("model").get());
//        assertEquals("XLE", request.headers().firstValue("generation").get());
//        assertEquals("Sport", request.headers().firstValue("modification").get());
//        assertEquals("1.8L", request.headers().firstValue("engine").get());
//        assertEquals("2022", request.headers().firstValue("year").get());
//        assertEquals("save", request.headers().firstValue("save").get());
//        assertEquals("application/json", request.headers().firstValue("Content-Type").get());
    }

    @Test
    void findCarById() {
    }
}
