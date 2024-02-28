package com.kammatysiak.medicalclinic.exceptions;

import org.springframework.http.HttpStatus;

public class VisitDoesNotExistException extends MedicalClinicException {

    public VisitDoesNotExistException(String message, HttpStatus status) {
        super(message, status);
    }
}
