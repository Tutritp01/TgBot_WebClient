package com.tutrit.webclient.config;

import com.tutrit.bean.Car;
import com.tutrit.gateway.CarGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyMocks {

    @Bean
    public CarGateway carGateway() {
        return new CarGateway() {

            @Override
            public Car findCarById(final String carId) {
                return new Car(
                        "12345",
                        "owner",
                        "vin",
                        "plateNumber",
                        "brand",
                        "model",
                        "generation",
                        "modification",
                        "engine",
                        2002
                );
            }

            @Override
            public Car saveCar(final Car car) {
                return new Car(
                        "12345",
                        "owner",
                        "vin",
                        "plateNumber",
                        "brand",
                        "model",
                        "generation",
                        "modification",
                        "engine",
                        2002
                );
            }
        };
    }
}
