package com.kammatysiak.medicalclinic.exceptions;

import org.springframework.http.HttpStatus;

public class VisitNullFieldException extends MedicalClinicException {
    public VisitNullFieldException(String message, HttpStatus status) {
        super(message, status);
    }
}
