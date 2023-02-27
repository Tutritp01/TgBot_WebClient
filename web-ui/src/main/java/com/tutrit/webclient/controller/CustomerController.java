package com.tutrit.webclient.controller;

import com.tutrit.bean.Customer;
import com.tutrit.gateway.CustomerGateway;
import com.tutrit.httpclient.gateway.CustomerGateway.HttpCustomerGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@SpringBootApplication(scanBasePackages = "com.tutrit")
@Controller

public class CustomerController {
    @Autowired
    private CustomerGateway customerGateway;

    @GetMapping("/customers")
    public ModelAndView showEmptyForm() {
        var mov = new ModelAndView();
        mov.addObject("customer", new Customer(null, null, null, null, null));
        mov.setViewName("customer-form");
        return mov;
    }

    @PostMapping("/customers")
    public String actionsOnTheCustomer(@RequestParam String customerId,
                                       @RequestParam String name,
                                       @RequestParam String city,
                                       @RequestParam String phoneNumber,
                                       @RequestParam String email,
                                       @RequestParam Optional<String> save,
                                       @RequestParam Optional<String> delete,
                                       @RequestParam Optional<String> update) {
        var customer = new Customer(customerId, name, city, phoneNumber, email);

        save.ifPresent(i -> {
            customerGateway.saveCustomer(customer);
        });
        delete.ifPresent(i -> {
            customerGateway.deleteCustomerById(customerId);
        });
        update.ifPresent(i -> {
            customerGateway.updateCustomer(customer);
        });
        return "redirect:/customers";
    }

    @GetMapping("/customers/{customerId}")
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

}
