package com.tutrit.interfaces;

public interface Version {

    String getWebCoreName();
    String getWebCoreVersion();

    String getWebUiName();
    String getWebUiVersion();

    String getHttpClientName();
    String getHttpClientVersion();

    default ModuleInfo moduleInfo() {
        return new ModuleInfo();
    }

}
