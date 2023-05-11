package com.tutrit.version;


import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Configuration;

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
