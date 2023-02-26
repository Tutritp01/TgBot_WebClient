package com.tutrit.bean;

public record Customer(String customerId,
                       String name,
                       String city,
                       String phoneNumber,
                       String email) {
}
