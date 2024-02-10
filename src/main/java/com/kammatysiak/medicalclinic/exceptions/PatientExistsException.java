package com.kammatysiak.medicalclinic.exceptions;

import org.springframework.http.HttpStatus;


public class PatientExistsException extends MedicalClinicException {
    public PatientExistsException(String message, HttpStatus status) {
        super(message, status);
    }
}
