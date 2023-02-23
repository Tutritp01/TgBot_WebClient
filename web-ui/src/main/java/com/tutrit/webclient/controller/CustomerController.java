package com.tutrit.webclient.controller;

import com.tutrit.bean.Customer;
import com.tutrit.gateway.CustomerGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
public class CustomerController {
    @Autowired(required = false)
    private CustomerGateway customerGateway;

    @GetMapping("customers/{customerId}")
    public ModelAndView findCustomerById(@PathVariable String customerId) {
        var mov = new ModelAndView();
        customerGateway.findCustomerById(customerId)
                .ifPresentOrElse(
                        c -> mov.addObject("customer", c),
                        () -> {
                            mov.addObject("customer", new Customer(null, null, null, null, null));
                            mov.addObject("error", "Customer not found");
                        });

        mov.setViewName("customer-form");
        return mov;
    }

    @PostMapping("/customers/{id}")
    public String saveCustomer(@PathVariable String id,
                               @RequestParam String customerId,
                               @RequestParam String name,
                               @RequestParam String city,
                               @RequestParam String phoneNumber,
                               @RequestParam String email,
                               @RequestParam Optional<String> save,
                               @RequestParam Optional<String> delete) {
        var customer = new Customer(customerId, name, city, phoneNumber, email);

        save.ifPresent(i -> customerGateway.saveCustomer(customer));
        delete.ifPresent(i -> customerGateway.deleteCustomerById(customerId));

        return "redirect:/customers/" + id;
    }

    @PostMapping("/customers")
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer newCustomer) {
        var customer = customerGateway.saveCustomer(newCustomer);
        return new ResponseEntity<>(customer, HttpStatus.CREATED);
    }
}
