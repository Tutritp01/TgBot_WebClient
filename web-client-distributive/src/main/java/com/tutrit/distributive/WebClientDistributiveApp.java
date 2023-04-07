package com.tutrit.distributive;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages  ="com.tutrit")
public class WebClientDistributiveApp {

    public static void main(String[] args) {
        final ConfigurableApplicationContext ctx = SpringApplication.run(WebClientDistributiveApp.class, args);
        System.out.println(ctx);
    }
}

