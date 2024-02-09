package com.kammatysiak.medicalclinic.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class PatientNullFieldException extends MedicalClinicException {
    public PatientNullFieldException(String message, HttpStatus status) {
        super(message, status);
    }
}
