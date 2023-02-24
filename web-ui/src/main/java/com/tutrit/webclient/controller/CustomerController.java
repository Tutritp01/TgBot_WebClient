package com.tutrit.webclient.controller;

import com.tutrit.bean.Customer;
import com.tutrit.gateway.CustomerGateway;
import com.tutrit.httpclient.gateway.CustomerGateway.HttpCustomerGateway;
import org.springframework.http.server.reactive.HttpHeadResponseDecorator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.Optional;

@Controller
public class CustomerController {
    //    @Autowired(required = false)
    private CustomerGateway customerGateway = new HttpCustomerGateway();

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
                                  @RequestParam Optional<String> delete) {
        var customer = new Customer(customerId, name,city, phoneNumber, email);

        save.ifPresent(i -> {
            try {
                customerGateway.saveCustomer(customer);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        delete.ifPresent(i -> customerGateway.deleteCustomerById(customerId));
        return "redirect:/customers";
    }

    @GetMapping("/customers/{customerId}")
    public ModelAndView findCustomerById(@PathVariable String customerId) throws IOException, InterruptedException {
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

        save.ifPresent(i -> {
            try {
                customerGateway.saveCustomer(customer);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        delete.ifPresent(i -> customerGateway.deleteCustomerById(customerId));

        return "redirect:/customers/" + id;
    }
}

