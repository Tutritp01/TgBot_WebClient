package com.tutrit.gateway;

import com.tutrit.bean.Customer;

public interface CustomerGateway {
    Customer saveCustomer (Customer customer);
    Customer findCustomerById(String customerId);
}
