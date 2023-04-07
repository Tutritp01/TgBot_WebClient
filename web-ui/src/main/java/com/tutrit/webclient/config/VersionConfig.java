package com.tutrit.webclient.config;

import com.tutrit.interfaces.ModuleInfo;
import com.tutrit.interfaces.Version;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:http-client.properties")
@PropertySource("classpath:web-ui.properties")
@PropertySource("classpath:web-core.properties")
public class VersionConfig implements Version {
    @Value("${web-core.app.name}")
    private String webCoreName;

    @Value("${web-core.app.version}")
    private String webCoreVersion;

    @Override
    public String getWebCoreName() {
        return webCoreName;
    }

    @Override
    public String getWebCoreVersion() {
        return webCoreVersion;
    }


    @Value("${web-ui.app.name}")
    private String webUiName;

    @Value("${web-ui.app.version}")
    private String webUiVersion;

    @Override
    public String getWebUiName() {
        return webUiName;
    }


    @Override
    public String getWebUiVersion() {
        return webUiVersion;
    }


    @Value("${http-client.app.name}")
    private String httpClientName;

    @Value("${http-client.app.version}")
    private String httpClientVersion;

    @Override
    public String getHttpClientName() {
        return httpClientName;
    }

    @Override
    public String getHttpClientVersion() {
        return httpClientVersion;
    }

    @Override
    public ModuleInfo moduleInfo() {
        var moi = new ModuleInfo();
        moi.setDescription("User web interface");
        moi.setModuleType("ui");
        return moi;
    }
}
