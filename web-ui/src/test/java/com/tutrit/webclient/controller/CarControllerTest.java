package com.tutrit.webclient.controller;

import com.tutrit.bean.Car;
import com.tutrit.config.MySpringContext;
import com.tutrit.gateway.CarGateway;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest(classes = MySpringContext.SpringConfig.class)
class CarControllerTest {

    @MockBean
    CarGateway carGateway;
    @Autowired
    MockMvc mockMvc;

    @Test
    void findCarById() throws Exception {
        when(carGateway.findCarById("id1")).thenReturn(makeCar());

        final MvcResult result = mockMvc.perform(get("/cars/id1"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("car", Matchers.equalTo(makeCar().get())))
                .andExpect(view().name("car-form"))
                .andReturn();

        Car actualCar = (Car) Objects.requireNonNull(result.getModelAndView()).getModel().get("car");
        assertEquals(makeCar().get(), actualCar);
    }

    @Test
    void saveCar() throws Exception {
        when(carGateway.saveCar(new Car("id1", "owner1", "vin1", "plateNumber1", "brand1", "model1", "1g", "mod1", "engine1", 2001))).thenReturn(makeCar());

        mockMvc.perform(post("/cars/id1")
                        .param("carId", "id1")
                        .param("owner", "owner1")
                        .param("vin", "vin1")
                        .param("plateNumber", "plateNumber1")
                        .param("brand", "brand1")
                        .param("model", "model1")
                        .param("generation", "1g")
                        .param("modification", "mod1")
                        .param("engine", "engine1")
                        .param("year", "2001")
                        .param("save", "push"))
                .andExpect(view().name("redirect:/cars/id1"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        Mockito.verify(carGateway).saveCar(makeCar().get());
    }

    private Optional<Car> makeCar() {
        Optional<Car> car = Optional.of(new Car("id1", "owner1", "vin1", "plateNumber1", "brand1", "model1", "1g", "mod1", "engine1", 2001));
        return car;
    }
}
