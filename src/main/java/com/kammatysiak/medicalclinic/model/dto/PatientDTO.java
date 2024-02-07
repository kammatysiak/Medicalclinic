package com.kammatysiak.medicalclinic.model.dto;

import com.kammatysiak.medicalclinic.model.entity.Patient;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class PatientDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private LocalDate birthday;
}
