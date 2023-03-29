package com.tutrit.config;

import com.tutrit.interfaces.AbstractVersionConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:web-core.properties")
public class WebCoreVersionConfig extends AbstractVersionConfig {
    public WebCoreVersionConfig(@Value("${web-core.app.name}") String name,
                                @Value("${web-core.app.version}") String version) {
        super(name, version);
    }
}
