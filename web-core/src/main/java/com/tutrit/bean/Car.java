package com.tutrit.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.soabase.recordbuilder.core.RecordBuilder;

@JsonIgnoreProperties(ignoreUnknown = true)
@RecordBuilder
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
