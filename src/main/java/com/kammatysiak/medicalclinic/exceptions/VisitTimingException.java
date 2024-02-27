package com.kammatysiak.medicalclinic.exceptions;

import org.springframework.http.HttpStatus;

public class VisitTimingException extends MedicalClinicException {
    public VisitTimingException(String message, HttpStatus status) {
        super(message, status);
    }
}
