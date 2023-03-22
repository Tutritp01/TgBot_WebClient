package com.tutrit.webclient.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.info.InfoEndpoint;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class InfoController {

    @Autowired
    private InfoEndpoint infoEndpoint;

    @GetMapping("/info")
    public ModelAndView getInfo() {
        var mov = new ModelAndView();
        mov.addObject("info", infoEndpoint.info());
        mov.setViewName("info");
        return mov;

    }

}
