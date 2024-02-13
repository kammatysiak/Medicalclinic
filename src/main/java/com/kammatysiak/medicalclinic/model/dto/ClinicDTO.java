package com.kammatysiak.medicalclinic.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ClinicDTO {
    private String name;

    private String city;

    private String postCode;

    private String street;

    private String buildingNumber;
}