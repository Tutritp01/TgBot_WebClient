package com.tutrit.webclient.controller;

import com.tutrit.webclient.exception.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DummyController {

    @GetMapping("/dummy")
    public String dummyEndpoint() {
        return "status:ok21";
    }

    @GetMapping("/dummy2")
    public String dummy2() {
        throw new RuntimeException("Something goes wrong");
    }

    @GetMapping("/dummy3")
    public String dummy3() {
        throw new AccessDeniedException();
    }
}
