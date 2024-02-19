package com.kammatysiak.medicalclinic.exceptions;

import org.springframework.http.HttpStatus;

public class ClinicDoesNotExistException extends MedicalClinicException {

    public ClinicDoesNotExistException(String message, HttpStatus status) {
        super(message, status);
    }
}
