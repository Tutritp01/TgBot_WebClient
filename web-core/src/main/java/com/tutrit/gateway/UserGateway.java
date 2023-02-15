package com.tutrit.gateway;

import com.tutrit.bean.User;

public interface UserGateway {
    User findUserById(String id);
    User saveUser(User user);

}
