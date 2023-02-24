package com.tutrit.webclient.controller;

import com.tutrit.bean.Customer;
import com.tutrit.gateway.CustomerGateway;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

@Configuration
@Controller
public class CustomerController {
    //    @Autowired(required = false)
    private CustomerGateway customerGateway = new CustomerGateway() {
        @Override
        public Optional<Customer> saveCustomer(final Customer customer) {
            return Optional.empty();
        }

        @Override
        public Optional<Customer> findCustomerById(final String customerId) {
//            return Optional.of(new Customer("32", "Vlad", "Brest", "+3754478937721", "VladVrest@mail.com"));
            return Optional.empty();
        }

        public Optional<Customer> findCustomerByIdAndValidateId(final String customerId,
                                                                final List<String> errors) {
            errors.add(customerId + " is not valid");
            return Optional.empty();
        }

        @Override
        public boolean deleteCustomerById(final String customerId) {
            return false;
        }
    };

    @GetMapping("customers/{customerId}")
    public ModelAndView findCustomerById(@PathVariable String customerId) {
        var errors = new ArrayList<String>();
        var mov = new ModelAndView();
        customerGateway
                .findCustomerByIdAndValidateId(customerId, errors)
                .ifPresentOrElse(
                        addCustomerToModel(mov),
                        addEmptyCustomerEndNotFoundError(errors, mov));
        addErrorsToModel(errors, mov);
        setViewNameToModel(mov);
        return mov;
    }

    private void setViewNameToModel(final ModelAndView mov) {
        mov.setViewName("customer-form.html");
    }

    private void addErrorsToModel(final ArrayList<String> errors, final ModelAndView mov) {
        mov.addObject("errors", errors);
    }

    private Runnable addEmptyCustomerEndNotFoundError(final ArrayList<String> errors, final ModelAndView mov) {
        return () -> {
            mov.addObject("customer", new Customer(null, null, null, null, null));
            errors.add("User not found");
        };
    }

    private Consumer<Customer> addCustomerToModel(final ModelAndView mov) {
        return c -> mov.addObject("customer", c);
    }

    @PostMapping("/customers/{id}")
    public String saveCustomer(@PathVariable String id,
                               HttpServletRequest r,
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


}
