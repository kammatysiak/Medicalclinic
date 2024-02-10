package com.kammatysiak.medicalclinic.exceptions;

import org.springframework.http.HttpStatus;


public class PatientNullFieldException extends MedicalClinicException {
    public PatientNullFieldException(String message, HttpStatus status) {
        super(message, status);
    }
}
