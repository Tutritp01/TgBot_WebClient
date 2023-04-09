package com.tutrit.version;

public interface Version {

   default ModuleInfo getInfoVersion(){
        return new ModuleInfo();
    }

}
