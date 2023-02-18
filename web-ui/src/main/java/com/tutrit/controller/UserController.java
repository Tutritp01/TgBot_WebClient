package com.tutrit.controller;


import com.tutrit.bean.User;
import com.tutrit.gateway.UserGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
public class UserController {

    @Autowired(required = false)
    private UserGateway userGateway;

    @GetMapping("users/{userId}")
    public ModelAndView findUserById(@PathVariable String userId){
        var mov = new ModelAndView();
        mov.addObject("user",userGateway.findUserById(userId));
        mov.setViewName("user-form.html");
        return mov;
    }
    @PostMapping("/users/{id}")
    public String saveUser(@PathVariable String id,
                               @RequestParam String userId,
                               @RequestParam String name,
                               @RequestParam String phoneNumber,
                               @RequestParam Optional<String> save,
                               @RequestParam Optional<String> delete) {
        var user = new User(userId, name, phoneNumber);
        save.ifPresent(i -> userGateway.saveUser(user));
        delete.ifPresent(i -> userGateway.deleteUserById(userId));

        return "redirect:/users/" + id;
    }


}
