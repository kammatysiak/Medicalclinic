package com.kammatysiak.medicalclinic.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class DoctorCreateDTO {

    private String email;
    private String firstName;
    private String lastName;
    private String specialization;
    private String password;
    private List<Long> clinicIds;
}
