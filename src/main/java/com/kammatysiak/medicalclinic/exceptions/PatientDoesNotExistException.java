package com.kammatysiak.medicalclinic.exceptions;

import org.springframework.http.HttpStatus;


public class PatientDoesNotExistException extends MedicalClinicException {
    public PatientDoesNotExistException(String message, HttpStatus status) {
        super(message, status);
    }
}
