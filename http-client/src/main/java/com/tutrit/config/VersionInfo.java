package com.tutrit.config;

import com.tutrit.interfaces.ModuleInfo;
import com.tutrit.interfaces.Version;
import org.springframework.stereotype.Component;

@Component
public class VersionInfo implements Version {
    @Override
    public String getWebCoreName() {
        return null;
    }

    @Override
    public String getWebCoreVersion() {
        return null;
    }

    @Override
    public String getWebUiName() {
        return null;
    }

    @Override
    public String getWebUiVersion() {
        return null;
    }

    @Override
    public String getHttpClientName() {
        return null;
    }

    @Override
    public String getHttpClientVersion() {
        return null;
    }

    @Override
    public ModuleInfo moduleInfo() {
        var moi = new ModuleInfo();
        moi.setDescription("HTTP protocol to service api");
        moi.setModuleType("gateway");
        return moi;
    }
}
