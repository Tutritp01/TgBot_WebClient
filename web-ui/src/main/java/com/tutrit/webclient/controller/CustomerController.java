package com.tutrit.webclient.controller;

import com.tutrit.bean.Customer;
import com.tutrit.gateway.CustomerGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;
import java.util.UUID;

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
    public String createNewCustomer(@RequestParam String customerId,
                                    @RequestParam String name,
                                    @RequestParam String city,
                                    @RequestParam String phoneNumber,
                                    @RequestParam String email,
                                    @RequestParam Optional<String> save,
                                    @RequestParam Optional<String> delete,
                                    @RequestParam Optional<String> update) {

        save.ifPresent(i -> {
            saveCustomer(String.valueOf(UUID.randomUUID()), name, city, phoneNumber, email, customerId);
        });
        delete.ifPresent(i -> {
            customerGateway.deleteCustomerById(customerId);
        });
        update.ifPresent(i -> {
            updateCustomer(customerId,name,phoneNumber,email,city);
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

    @PostMapping("/customers/{id}")
    public String saveCustomer(@PathVariable String id,
                               @RequestParam String customerId,
                               @RequestParam String name,
                               @RequestParam String city,
                               @RequestParam String phoneNumber,
                               @RequestParam String email) {
        var customer = new Customer(customerId, name, city, phoneNumber, email);

        customerGateway.saveCustomer(customer);

        return "redirect:/customers/" + id;
    }

    @PostMapping("/customers/{customerId}")
    public String updateCustomer(@PathVariable String customerId,
                                 @RequestParam String name,
                                 @RequestParam String city,
                                 @RequestParam String phoneNumber,
                                 @RequestParam String email) {
        var customer = new Customer(customerId, name, city, phoneNumber, email);

        customerGateway.updateCustomer(customer);

        return "redirect:/customers/";
    }
}
