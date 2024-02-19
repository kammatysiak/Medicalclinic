package com.kammatysiak.medicalclinic.exceptions;

import org.springframework.http.HttpStatus;

public class DoctorDoesNotExistException extends MedicalClinicException {
    public DoctorDoesNotExistException(String message, HttpStatus status) {
        super(message, status);
    }
}
