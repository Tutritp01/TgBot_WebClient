package com.tutrit.webClientRun;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages  ="com.tutrit")
public class ApplicationRun {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationRun.class, args);
    }

}

