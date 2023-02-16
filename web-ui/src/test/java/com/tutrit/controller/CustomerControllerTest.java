package com.tutrit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tutrit.bean.Customer;
import com.tutrit.config.MySpringContext;
import com.tutrit.gateway.CustomerGateway;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@AutoConfigureMockMvc
@SpringBootTest(classes = MySpringContext.SpringConfig.class)
class CustomerControllerTest {

    @MockBean
    CustomerGateway customerGateway;
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;


    @Test
    void findCustomerById() throws Exception {
        when(customerGateway.findCustomerById("234")).thenReturn(createCustomer());

        final MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.get("/customers/234"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(model().attribute("customer", Matchers.equalTo(createCustomer())))
                .andExpect(view().name("customer-form.html"))
                .andReturn();
//        String body = result.getResponse().getContentAsString();
//        var actualCustomer = objectMapper.readValue(body, Customer.class);
                Customer actualCustomer = (Customer) Objects.requireNonNull(result.getModelAndView()).getModel().get("customer");
        assertEquals(createCustomer(), actualCustomer);
    }

    @Test
    void saveCar() {
    }

    private Customer createCustomer() {
        return new Customer("id", "name", "city", "phoneNumber", "email");
    }
}