package com.tutrit.webclient.config;

import com.tutrit.interfaces.ModuleInfo;
import com.tutrit.interfaces.Version;
import org.springframework.stereotype.Component;

import static com.tutrit.interfaces.ModuleType.WEB_UI;

@Component
public class WebUiVersionConfig implements Version {
    @Override
    public ModuleInfo getInfoVersion() {
        var info = new ModuleInfo();
        info.setVersion("3.3.3");
        info.setDescription("Description about Web Ui");
        info.setModuleType(WEB_UI);
        return info;
    }
}
