package com.kammatysiak.medicalclinic.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class DoctorDTO {

    private String email;
    private String firstName;
    private String lastName;
    private String specialization;
    private List<Long> clinicIds;
}
