package com.kammatysiak.medicalclinic.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class PatientExistsException extends MedicalClinicException{
    public PatientExistsException(String message, HttpStatus status) {
        super(message, status);
    }
}
