package com.kammatysiak.medicalclinic.exceptions;

import org.springframework.http.HttpStatus;

public class DoctorExistsException extends MedicalClinicException {
    public DoctorExistsException(String message, HttpStatus status) {
        super(message, status);
    }
}
