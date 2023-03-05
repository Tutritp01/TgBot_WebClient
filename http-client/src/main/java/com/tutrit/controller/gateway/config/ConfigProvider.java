package com.tutrit.controller.gateway.config;

import org.springframework.stereotype.Component;

@Component
public class ConfigProvider {

    private final WebClientUrlConfig webClientUrlConfig;

    public ConfigProvider(final WebClientUrlConfig webClientUrlConfig) {
        this.webClientUrlConfig = webClientUrlConfig;
    }

    public String getUrl() {
        return webClientUrlConfig.getUrl();
    }
}
