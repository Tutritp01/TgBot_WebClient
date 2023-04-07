package com.tutrit.distributive.version;

import com.tutrit.interfaces.ModuleInfo;
import com.tutrit.interfaces.Version;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class VersionProviderController {
    public final List<Version> versions;

    public VersionProviderController(final List<Version> versions) {
        this.versions = versions;
    }

    @GetMapping("/infomikas")
    public List<ModuleInfo> getVersion() {
        return versions.stream()
                .map(v -> v.moduleInfo())
                .collect(Collectors.toList());
    }
}
