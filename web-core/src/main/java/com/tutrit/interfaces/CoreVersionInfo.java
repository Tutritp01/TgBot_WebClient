package com.tutrit.interfaces;

import org.springframework.stereotype.Component;

@Component
public class CoreVersionInfo implements Version{
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
        moi.setDescription("Core service");
        moi.setModuleType("core");
        return moi;
    }
}
