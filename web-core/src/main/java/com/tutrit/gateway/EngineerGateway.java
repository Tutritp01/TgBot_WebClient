package com.tutrit.gateway;

import com.tutrit.bean.Engineer;

import java.util.Optional;

public interface EngineerGateway {
    Engineer saveEngineer(Engineer engineer);

    Optional<Engineer> findEngineerById(String id);

    boolean deleteEngineerById(String id);
}
