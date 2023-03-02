package com.tutrit.httpclient.gateway.CustomerGateway;

import com.tutrit.bean.Customer;
import com.tutrit.gateway.CustomerGateway;
import com.tutrit.httpclient.gateway.config.EndpointConfig;
import com.tutrit.httpclient.gateway.config.SpringContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;


@SpringBootTest(classes = SpringContext.SpringConfig.class)
class HttpCustomerGatewayTest {
    @Autowired
    CustomerGateway customerGateway;
    @MockBean
    EndpointConfig endpointConfig;

    @Test
    void saveCustomer() {
        when(endpointConfig.getRestApiUrl()).thenReturn("http://localhost:8080/customers");
        RuntimeException thrown = assertThrows(
                RuntimeException.class,
                () -> customerGateway.saveCustomer(createCustomer())
        );
        assertTrue(thrown.getMessage().contentEquals("java.net.ConnectException"));
    }

    @Test
    void findCustomerById() {
        when(endpointConfig.getRestApiUrl()).thenReturn("http://localhost:8080/customers");
        RuntimeException thrown = assertThrows(
                RuntimeException.class,
                () -> customerGateway.findCustomerById("12")
        );
        assertTrue(thrown.getMessage().contentEquals("java.net.ConnectException"));
    }

    @Test
    void deleteCustomerById() {
    }

    private Customer createCustomer() {
        return new Customer("23453434", "name", "city", "phoneNumber", "email");
    }

}
