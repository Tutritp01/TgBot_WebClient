package com.tutrit.config;

import com.tutrit.version.ModuleInfo;
import com.tutrit.version.Version;
import org.springframework.stereotype.Component;

import static com.tutrit.version.ModuleType.HTTP_CLIENT;

@Component
public class HttpClientVersionConfig implements Version {
    @Override
    public ModuleInfo getInfoVersion() {
        var info = new ModuleInfo();
        info.setVersion("2.2.2");
        info.setDescription("Description about HttpClient");
        info.setModuleType(HTTP_CLIENT);
        return info;
    }
}
