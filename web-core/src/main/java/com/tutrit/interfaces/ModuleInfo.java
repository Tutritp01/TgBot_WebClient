package com.tutrit.interfaces;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:META-INF/build-info.properties")
public class ModuleInfo {
    private String moduleType; // TODO: 4/7/23 replace with enum
    private String artifact;
    private String group;
    private String java;
    private String name;
    @Value("${build.time}")
    private String time;
    private String version;
    private String description;

    public String getModuleType() {
        return moduleType;
    }

    public void setModuleType(final String moduleType) {
        this.moduleType = moduleType;
    }

    public String getArtifact() {
        return artifact;
    }

    public void setArtifact(final String artifact) {
        this.artifact = artifact;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(final String group) {
        this.group = group;
    }

    public String getJava() {
        return java;
    }

    public void setJava(final String java) {
        this.java = java;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(final String version) {
        this.version = version;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }
}
