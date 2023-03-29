package com.tutrit.config;

import com.tutrit.interfaces.AbstractVersionConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:http-client.properties")
public class HttpClientVersionConfig extends AbstractVersionConfig {
    public HttpClientVersionConfig(@Value("${http-client.app.name}") String name,
                                   @Value("${http-client.app.version}") String version) {
        super(name, version);
    }
}
