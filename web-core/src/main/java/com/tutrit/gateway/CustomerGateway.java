package com.tutrit.gateway;

import com.tutrit.bean.Customer;

import java.util.Optional;

public interface CustomerGateway {
    Customer saveCustomer(Customer customer);

    Optional<Customer> findCustomerById(String customerId);

    boolean deleteCustomerById(String customerId);

    Optional<Customer> updateCustomer(Customer customer);
}
