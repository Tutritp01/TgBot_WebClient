package com.tutrit.webclient.config;

import com.tutrit.interfaces.VersionInterface;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:web-ui.properties")
public class WebUiVersionConfig implements VersionInterface {
    @Value("${web-ui.app.name}")
    private String name;

    @Value("${web-ui.app.version}")
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
