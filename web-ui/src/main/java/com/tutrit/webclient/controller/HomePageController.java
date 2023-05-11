package com.tutrit.webclient.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tutrit.bean.Order;
import com.tutrit.interfaces.ModuleInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpTimeoutException;
import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.net.http.HttpClient.newHttpClient;

@Controller
public class HomePageController {

    @Autowired
    ObjectMapper objectMapper;

    @GetMapping("/")
    public ModelAndView openHomePage() {
        List<ModuleInfo> moduleInfoList = this.getModuleInfo();
        Map<String, ModuleInfo> infoMap = moduleInfoList.stream()
                .filter(i -> i.getModuleType() != null)
                .collect(Collectors.toMap(i -> i.getModuleType(), Function.identity()));
        var mov = new ModelAndView();
        mov.setViewName("starter");
        mov.addObject("ui", infoMap.get("ui"));
        mov.addObject("core", infoMap.get("core"));
        mov.addObject("gateway", infoMap.get("gateway"));
        return mov;

    }

    private List<ModuleInfo> getModuleInfo() {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8100/infomikas"))
                .header("Content-Type", "application/json")
                .GET()
                .timeout(Duration.ofSeconds(2))
                .build();
        try {
            final HttpResponse<String> response = newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            String body = response.body();
            List<ModuleInfo> orderList = objectMapper.readValue(body, new TypeReference<List<ModuleInfo>>() {});
            return orderList;

        } catch (HttpTimeoutException e) {
            // TODO: 3/26/23 add pop up "Unknown error" and log exception
            e.printStackTrace();
        } catch (Exception e) {
            // TODO: 3/26/23 add pop up "Unknown error" and log exception
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

}

