package com.tutrit.httpclient.gateway.config;

import org.springframework.stereotype.Component;

@Component
public class ConfigProvider {
    private final HttpClientConfig httpClientConfig;

    public ConfigProvider(final HttpClientConfig httpClientConfig) {
        this.httpClientConfig = httpClientConfig;
    }

    public String getUrl(){
        return httpClientConfig.getRestApiUrl();
    }
}
