package com.tutrit.distributive.config;

import com.tutrit.interfaces.ModuleInfo;
import com.tutrit.interfaces.Version;
import org.springframework.stereotype.Component;

import static com.tutrit.interfaces.ModuleType.WEB_CLIENT_DISTRIBUTIVE;

@Component
public class WebDistributiveVersionConfig implements Version {
    @Override
    public ModuleInfo getInfoVersion() {
        var info = new ModuleInfo();
        info.setVersion("1.1.1");
        info.setDescription("Description about Distributive ");
        info.setModuleType(WEB_CLIENT_DISTRIBUTIVE);
        return info;
    }
}
