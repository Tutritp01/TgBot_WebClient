package com.tutrit.webClientRun;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages  ="com.tutrit")
public class ApplicationRun {
    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(ApplicationRun.class, args);
        System.out.println(ctx);
    }

}

