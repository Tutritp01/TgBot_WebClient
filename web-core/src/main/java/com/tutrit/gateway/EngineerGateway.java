package com.tutrit.gateway;

import com.tutrit.bean.Engineer;

public interface EngineerGateway {
    Engineer saveEngineer(Engineer engineer);
    Engineer findEngineerById(String id);
    Iterable<Engineer> findAllEngineer();
    Engineer update(String id);
    boolean deleteEngineer(Engineer engineer);
    boolean deleteEngineerById(String id);
}
