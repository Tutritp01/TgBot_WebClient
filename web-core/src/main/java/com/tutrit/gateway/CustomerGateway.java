package com.tutrit.gateway;

import com.tutrit.bean.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerGateway {
    Optional<Customer> saveCustomer (Customer customer);
    Optional<Customer> findCustomerById(String customerId);
    Optional<Customer> findCustomerByIdAndValidateId(String customerId, List<String> errors);
    boolean deleteCustomerById(String customerId);
}
