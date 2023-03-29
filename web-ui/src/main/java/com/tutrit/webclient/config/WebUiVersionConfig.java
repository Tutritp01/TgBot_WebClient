package com.tutrit.webclient.config;

import com.tutrit.interfaces.AbstractVersionConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:web-ui.properties")
public class WebUiVersionConfig extends AbstractVersionConfig {
    public WebUiVersionConfig(@Value("${web-ui.app.name}") String name,
                              @Value("${web-ui.app.version}") String version) {
        super(name, version);
    }
}
