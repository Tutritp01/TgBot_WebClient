package com.tutrit.version;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController

public class VersionController {

    public final List<Version> versionList;

    public VersionController(final List<Version> versionList) {
        this.versionList = versionList;
    }

    @GetMapping("/info")
    public List<ModuleInfo> getVersion() {
        return versionList.stream()
                .map(Version::getInfoVersion)
                .toList();
    }

}
