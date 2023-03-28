package com.tutrit.config;

import com.tutrit.interfaces.VersionInterface;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:web-core.properties")
public class WebCoreVersionConfig implements VersionInterface {
    @Value("${web-core.app.name}")
    private String name;

    @Value("${web-core.app.version}")
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
