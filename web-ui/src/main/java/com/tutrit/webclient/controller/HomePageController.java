package com.tutrit.webclient.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Controller
public class HomePageController {
    @GetMapping("/")
    public ModelAndView openHomePage() throws IOException, InterruptedException {
        String url = "http://localhost:8100/info";
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        var mov = new ModelAndView();
        mov.setViewName("starter");
        mov.addObject("infoResponse", response.body());
        return mov;

    }

}

