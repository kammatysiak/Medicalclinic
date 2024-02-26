package com.kammatysiak.medicalclinic.model.dto;

import com.kammatysiak.medicalclinic.model.entity.Clinic;
import com.kammatysiak.medicalclinic.model.entity.Doctor;
import com.kammatysiak.medicalclinic.model.entity.Patient;

import java.time.LocalDateTime;

public class VisitDTO {
    private Patient patient;
    private Doctor doctor;
    private Clinic clinic;
    private LocalDateTime visitStart;
    private LocalDateTime visitEnd;
}
