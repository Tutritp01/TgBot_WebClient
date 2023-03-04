package com.tutrit.webclient.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tutrit.bean.Customer;
import com.tutrit.gateway.CustomerGateway;
import com.tutrit.webclient.config.SpringContext;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@AutoConfigureMockMvc
@SpringBootTest(classes = SpringContext.SpringConfig.class)
class CustomerControllerTest {
    @MockBean
    CustomerGateway customerGateway;
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @Test
    void findCustomerById() throws Exception {
        when(customerGateway.findCustomerById("234"))
                .thenReturn(Optional.of((createCustomer())));

        final MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.get("/customers/234"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(model().attribute("customer", Matchers.equalTo(createCustomer())))
                .andExpect(view().name("customer-form"))
                .andReturn();
        Customer actualCustomer = (Customer) Objects.requireNonNull(result.getModelAndView()).getModel().get("customer");
        assertEquals(createCustomer(), actualCustomer);
    }

    @Test
    void findCustomerByIdIfCustomerNull() throws Exception {
        when(customerGateway.findCustomerById("231")).thenReturn((customerIsNull()));

        final MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.get("/customers/231"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(model().attribute("customer", Matchers.equalTo(expectedCustomerByNull())))
                .andExpect(view().name("customer-form"))
                .andExpect(model().attribute("error", Matchers.equalTo("Customer not found")))
                .andReturn();
        Customer actualCustomer = (Customer) Objects.requireNonNull(result.getModelAndView()).getModel().get("customer");
        assertEquals(expectedCustomerByNull(), actualCustomer);
    }

    @Test
    void saveCustomer() throws Exception {
        final MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders.post("/customers/23")
                        .param("customerId", "32")
                        .param("name", "Vlad")
                        .param("city", "Brest")
                        .param("phoneNumber", "+3754478937721")
                        .param("email", "VladVrest@mail.com")
                        .param("save", "push"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/customers"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        Mockito.verify(customerGateway).saveCustomer(verifyCustomer());
    }

    @Test
    void deleteCustomer() throws Exception {
        final MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders.post("/customers/23")
                        .param("customerId", "32")
                        .param("name", "Vlad")
                        .param("city", "Brest")
                        .param("phoneNumber", "+3754478937721")
                        .param("email", "VladVrest@mail.com")
                        .param("delete", "push"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/customers"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        Mockito.verify(customerGateway).deleteCustomerById("32");
    }

    private Customer createCustomer() {
        return new Customer("23453434", "name", "city", "phoneNumber", "email");
    }

    private Optional<Customer> customerIsNull() {
        return Optional.empty();
    }

    private Customer expectedCustomerByNull() {
        return new Customer(null, null, null, null, null);
    }

    private Customer verifyCustomer() {
        return new Customer("32", "Vlad", "Brest", "+3754478937721", "VladVrest@mail.com");
    }
}
