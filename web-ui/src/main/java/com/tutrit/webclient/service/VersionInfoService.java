package com.tutrit.webclient.service;


import com.tutrit.webclient.config.VersionConfig;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VersionInfoService {
    private final VersionConfig config;
    public VersionInfoService(final VersionConfig config) {
        this.config = config;
        List<String> list = new ArrayList<>();
        list.add(config.getHttpClientName() + " " + config.getHttpClientVersion());
        list.add(config.getWebCoreName() + " " + config.getWebCoreVersion());
        list.add(config.getWebUiName() + " " + config.getWebUiVersion());
    }

    public String getVersions() {
        StringBuilder sb = new StringBuilder();
        sb.append(config.getHttpClientName())
                .append(" ")
                .append(config.getHttpClientVersion())
                .append("\n")
                .append(config.getWebCoreName())
                .append(" ")
                .append(config.getWebCoreVersion())
                .append("\n")
                .append(config.getWebUiName())
                .append(" ")
                .append(config.getWebUiVersion());

        return sb.toString();
    }

}
