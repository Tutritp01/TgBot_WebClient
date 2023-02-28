package com.tutrit.bean;

public record Engineer(String engineerId,
                       String firstName,
                       String lastName,
                       String function,
                       String category,
                       String education,
                       Integer experience,
                       Integer generalExperience) {
}
