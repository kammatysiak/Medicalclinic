package com.kammatysiak.medicalclinic.exceptions;

import org.springframework.http.HttpStatus;

public class DoctorNullFieldException extends MedicalClinicException {
    public DoctorNullFieldException(String message, HttpStatus status) {
        super(message, status);
    }
}
