package com.tutrit.bean;

public record Car(String carId,
                  String owner,
                  String vin,
                  String plateNumber,
                  String brand,
                  String model,
                  String generation,
                  String modification,
                  String engine,
                  Integer year) {
}
