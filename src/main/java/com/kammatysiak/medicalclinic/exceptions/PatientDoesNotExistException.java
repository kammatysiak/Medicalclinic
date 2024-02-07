package com.kammatysiak.medicalclinic.exceptions;

import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class PatientDoesNotExistException extends MedicalClinicException{
    public PatientDoesNotExistException(String message, HttpStatus status) {
        super(message, status);
    }
}
