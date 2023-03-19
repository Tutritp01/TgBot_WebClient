package com.tutrit.webclient.controller;

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

    @GetMapping("/users/{userId}")
    public ModelAndView findUserById(@PathVariable String userId) {
        var mov = new ModelAndView();
        try {
            userGateway.findUserById(userId).ifPresentOrElse(
                    c -> mov.addObject("user", c),
                    () -> {
                        mov.addObject("user", new User(null, null, null));
                        mov.addObject("error_404", "User not found");
                    });
        } catch (Exception e) {
            mov.addObject("user", new User(null, null, null));
        }
        mov.setViewName("user-form");
        return mov;
    }

    @PostMapping("/users/{id}")
    public String saveUser(@PathVariable String id,
                           @RequestParam String name,
                           @RequestParam String phoneNumber,
                           @RequestParam Optional<String> save,
                           @RequestParam Optional<String> delete) {
        if("null".equals(id)) {
            id = null;
        }
        var user = new User(id, name, phoneNumber);
        // TODO: 3/19/23 refactor to clean&SOLID
        id = saveIfNewUser(save, user);
        updateUserIfSaveButtonPressed(save, user);
        doDeleteIfDeleteButtonPressed(delete, id);
        return "redirect:/users/"+ id;
    }

    private void updateUserIfSaveButtonPressed(final Optional<String> save, final User user) {
        save.ifPresent(i -> userGateway.saveUser(user));
    }

    private String saveIfNewUser(final Optional<String> save, final User user) {
        if (user.userId() == null && save.isPresent()) {
            User savedUser = userGateway.saveUser(user);
            return savedUser.userId();
        }
        return null;
    }

    private void doDeleteIfDeleteButtonPressed(final Optional<String> delete, final String id) {
        delete.ifPresent(i -> userGateway.deleteUserById(id));
    }
}
