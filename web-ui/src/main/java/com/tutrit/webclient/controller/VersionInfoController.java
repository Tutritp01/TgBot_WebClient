package com.tutrit.webclient.controller;

import com.tutrit.webclient.service.VersionInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VersionInfoController {
    @Autowired
    private VersionInfoService service;

    /**
     * Endpoint disabled and need to be rethought, meanwhile use VersionController
     * @return
     */
    //    @GetMapping("/info")
    public String getVersionInfo() {
        return service.getVersions();
    }
}
