package com.kammatysiak.medicalclinic.exceptions;

import org.springframework.http.HttpStatus;


public class PatientPasswordValidationException extends MedicalClinicException {
    public PatientPasswordValidationException(String message, HttpStatus status) {
        super(message, status);
    }
}
