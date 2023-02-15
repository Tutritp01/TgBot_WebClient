package com.tutrit.controller;


import com.tutrit.gateway.UserGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired(required = false)
    private UserGateway userGateway;

    @GetMapping("/{id}")
    public ModelAndView findUserById(@PathVariable String id){
        var mov = new ModelAndView();
        mov.addObject("user",userGateway.findUserById(id));
        mov.setViewName("user-form.html");
        return mov;
    }


}
