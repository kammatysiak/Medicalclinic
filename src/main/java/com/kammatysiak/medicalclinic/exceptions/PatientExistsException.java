package com.kammatysiak.medicalclinic.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class PatientExistsException extends MedicalClinicException {
    public PatientExistsException(String message, HttpStatus status) {
        super(message, status);
    }
}
