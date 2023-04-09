package com.tutrit.interfaces;

public interface Version {

   default ModuleInfo getInfoVersion(){
        return new ModuleInfo();
    }

}
