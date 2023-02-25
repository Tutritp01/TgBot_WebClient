package com.tutrit.gateway;

import com.tutrit.bean.User;

import java.util.Optional;

public interface UserGateway {
    Optional<User> findUserById(String userId);

    User saveUser(User user);

    boolean deleteUserById(String userId);
}
