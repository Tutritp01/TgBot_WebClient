package com.tutrit.gateway;

import com.tutrit.bean.User;

public interface UserGateway {
    User findUserById(String userId);

    User saveUser(User user);

    boolean deleteUserById(String userId);
}
