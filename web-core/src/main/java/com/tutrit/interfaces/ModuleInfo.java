package com.tutrit.interfaces;


import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
@Setter
public class ModuleInfo {

    private ModuleType moduleType;
    private String artifact;
    private String group;
    private String java;
    private String name;
    private String time;
    private String version;
    private String description;

}
