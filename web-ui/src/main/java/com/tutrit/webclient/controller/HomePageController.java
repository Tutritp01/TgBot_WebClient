package com.tutrit.webclient.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomePageController {
    @GetMapping("/")
    public ModelAndView openHomePage() {
        var mov = new ModelAndView();
        mov.setViewName("starter");
        return mov;

    }

}

