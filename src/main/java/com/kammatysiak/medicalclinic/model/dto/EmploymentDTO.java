package com.kammatysiak.medicalclinic.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmploymentDTO {

    private String doctorEmail;
    private String clinicName;
}
