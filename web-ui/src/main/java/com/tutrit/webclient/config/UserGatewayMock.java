package com.tutrit.webclient.config;

import com.tutrit.bean.User;
import com.tutrit.gateway.UserGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;


@Configuration
public class UserGatewayMock {
    @Bean
    public UserGateway userGateway() {
        return new UserGateway() {
            @Override
            public Optional<User> findUserById(final String userId) {
                return userId.equals("2") ?
                        Optional.of(new User("2", "KurtCobain", "+375294561234"))
                        : Optional.empty();
//           return Optional.of(new User("2", "KurtCobain", "+375294561234"));
//           return Optional.empty();
            }

            @Override
            public Optional<User> saveUser(User user) {
                return Optional.empty();
            }

            @Override
            public boolean deleteUserById(String userId) {
                return false;
            }


        };
    }


}
