package com.tutrit.distributive.version.controller;

import com.tutrit.interfaces.ModuleInfo;
import com.tutrit.interfaces.Version;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class VersionInfoController {

    public final List<Version> versionList;

    public VersionInfoController(final List<Version> versionList) {
        this.versionList = versionList;
    }

    @GetMapping("/info")
    public List<ModuleInfo> getVersion() {
        return versionList.stream()
                .map(Version::getInfoVersion)
                .toList();
    }

}
