package com.tutrit.gateway;

import com.tutrit.bean.Customer;

import java.io.IOException;
import java.util.Optional;

public interface CustomerGateway {
    Customer saveCustomer(Customer customer) throws IOException, InterruptedException;
    Optional<Customer> findCustomerById(String customerId) throws IOException, InterruptedException;
    boolean deleteCustomerById(String customerId);
}
