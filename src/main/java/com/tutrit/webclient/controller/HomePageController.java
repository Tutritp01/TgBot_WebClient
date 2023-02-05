package com.tutrit.webclient.controller;

import com.tutrit.webclient.bean.User;
import com.tutrit.webclient.gateway.EngineerGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
public class HomePageController {

    private final EngineerGateway engineerGateway;

    public HomePageController(final EngineerGateway engineerGateway) {
        this.engineerGateway = engineerGateway;
    }

    @GetMapping("/")
    public ModelAndView openHomePage() throws Exception {
        ModelAndView mov = new ModelAndView();
        try {
            mov.addObject("user", engineerGateway.getEngineer());
        } catch (Exception e) {
            // handle absence of information
            mov.addObject("user", new User(null, null));
        }
        mov.setViewName("index");
        return mov;
    }

    @PostMapping("/")
    public String saveEngineer(@RequestParam Optional<String> name, @RequestParam(required = false) Integer age) throws Exception {
        var user = new User(name.get(), age);
        engineerGateway.postEngineer(user);
        return "redirect:/";
    }

    @GetMapping("/user")
    public String openHomePage(Model model) {
        model.addAttribute("user", new User("Vlad", 15));
        return "index";
    }
}
