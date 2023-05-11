package com.tutrit.webclient.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tutrit.version.ModuleInfo;
import com.tutrit.version.ModuleType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.tutrit.version.ModuleType.*;
import static java.net.http.HttpClient.newHttpClient;

@Controller
public class HomePageController {

    @Autowired
    ObjectMapper objectMapper;

    @GetMapping("/")
    public ModelAndView openHomePage() {
        List<ModuleInfo> moduleInfoList = this.getModuleInfo();
        Map<ModuleType, ModuleInfo> infoMap = moduleInfoList.stream()
                .filter(i -> i.getModuleType() != null)
                .collect(Collectors.toMap(ModuleInfo::getModuleType, Function.identity()));
        var mov = new ModelAndView();
        mov.setViewName("starter");
        mov.addObject("webUi", infoMap.get(WEB_UI));
        mov.addObject("webCore", infoMap.get(WEB_CORE));
        mov.addObject("httpClient", infoMap.get(HTTP_CLIENT));
        mov.addObject("distributive", infoMap.get(WEB_CLIENT_DISTRIBUTIVE));
        return mov;

    }

    private List<ModuleInfo> getModuleInfo() {
        String url = "http://localhost:8100/info";
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .GET()
                .timeout(Duration.ofSeconds(2))
                .build();
        try {
            final HttpResponse<String> response = newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            String body = response.body();
            return objectMapper.readValue(body, new TypeReference<>() {
            });
        } catch (Exception e) {
            throw new RuntimeException("Error HttpResponse ");
        }
    }

}

