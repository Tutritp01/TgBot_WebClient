package com.tutrit.webclient.controller;

import com.tutrit.bean.Engineer;
import com.tutrit.gateway.EngineerGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
@RequestMapping("/engineer")
public class EngineerController {

    @Autowired(required = false)
    private EngineerGateway engineerGateway;

    @GetMapping("/{id}")
    public ModelAndView findEngineerById(@PathVariable String id) {
        var mov = new ModelAndView();
        mov.addObject("engineer", engineerGateway.findEngineerById(id));
        mov.setViewName("engineer-form");
        return mov;
    }

    @PostMapping("/{id}")
    public String saveEngineer(@PathVariable String id,
                          @RequestParam String engineerId,
                          @RequestParam String firstName,
                          @RequestParam String lastName,
                          @RequestParam String function,
                          @RequestParam String category,
                          @RequestParam String education,
                          @RequestParam Integer experience,
                          @RequestParam Integer generalExperience,
                          @RequestParam Optional<String> save,
                          @RequestParam Optional<String> delete) {

        Engineer engineer = new Engineer(engineerId, firstName, lastName, function, category, education, experience, generalExperience);

        save.ifPresent(i -> engineerGateway.saveEngineer(engineer));
        return "redirect:/engineers/" + id;
    }

}
