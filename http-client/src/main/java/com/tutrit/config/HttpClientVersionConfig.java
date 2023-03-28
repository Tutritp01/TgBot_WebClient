package com.tutrit.config;

import com.tutrit.interfaces.VersionInterface;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:http-client.properties")
public class HttpClientVersionConfig implements VersionInterface {
    @Value("${http-client.app.name}")
    private String name;

    @Value("${http-client.app.version}")
    private String version;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getVersion() {
        return version;
    }
}
