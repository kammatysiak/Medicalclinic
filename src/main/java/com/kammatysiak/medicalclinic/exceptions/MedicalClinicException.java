package com.kammatysiak.medicalclinic.exceptions;

import org.springframework.http.HttpStatus;


public class MedicalClinicException extends RuntimeException {

    private final HttpStatus status;

    public MedicalClinicException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
