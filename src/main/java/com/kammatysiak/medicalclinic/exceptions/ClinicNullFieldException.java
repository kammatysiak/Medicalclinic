package com.kammatysiak.medicalclinic.exceptions;

import org.springframework.http.HttpStatus;

public class ClinicNullFieldException extends MedicalClinicException {

    public ClinicNullFieldException(String message, HttpStatus status) {
        super(message, status);
    }
}
