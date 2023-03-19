package com.tutrtit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages  ="com.tutrit")
public class WebClientDistributiveApp {
    public static void main(String[] args) {
        SpringApplication.run(WebClientDistributiveApp.class, args);
    }
}

