package com.tutrit.webclient.service;

import com.tutrit.config.HttpClientVersionConfig;
import com.tutrit.config.WebCoreVersionConfig;
import com.tutrit.interfaces.AbstractVersionConfig;
import com.tutrit.webclient.config.WebUiVersionConfig;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VersionInfoService {
    private final List<AbstractVersionConfig> versions = new ArrayList<>();

    public VersionInfoService(final WebUiVersionConfig webUiVersionConfig,
                              final WebCoreVersionConfig webCoreVersionConfig,
                              final HttpClientVersionConfig httpClientVersionConfig
                              ) {
        versions.add(webUiVersionConfig);
        versions.add(webCoreVersionConfig);
        versions.add(httpClientVersionConfig);
    }

    public String getVersions() {
        return versions.stream()
                .map(s -> s.getName() + " " + s.getVersion() + "\n")
                .collect(Collectors.joining());
    }

}
