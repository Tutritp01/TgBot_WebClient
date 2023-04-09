package com.tutrit.config;

import com.tutrit.interfaces.ModuleInfo;
import com.tutrit.interfaces.Version;
import org.springframework.stereotype.Component;

import static com.tutrit.interfaces.ModuleType.WEB_CORE;

@Component
public class WebCoreVersionConfig implements Version {
    @Override
    public ModuleInfo getInfoVersion() {
        ModuleInfo info = new ModuleInfo();
        info.setVersion("1.1.1");
        info.setDescription("Description about Web Core ");
        info.setModuleType(WEB_CORE);
        return info;
    }
}
