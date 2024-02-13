package com.kammatysiak.medicalclinic.exceptions;

import org.springframework.http.HttpStatus;

public class ClinicExistsException extends MedicalClinicException {
    public ClinicExistsException(String message, HttpStatus status) {
        super(message, status);
    }
}
