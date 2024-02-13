package com.kammatysiak.medicalclinic.model.dto;

import com.kammatysiak.medicalclinic.model.entity.Clinic;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class DoctorDTO {

    private String email;
    private String firstName;
    private String lastName;
    private String specialization;
    private Set<Clinic> employmentsInClinics;
}
