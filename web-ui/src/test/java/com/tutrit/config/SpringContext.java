package com.tutrit.config;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

public class SpringContext {
    @Configuration
    @SpringBootConfiguration
    @EnableAutoConfiguration
    @ComponentScan(basePackages = "com.tutrit")
    public static class SpringConfig {

    }
}
