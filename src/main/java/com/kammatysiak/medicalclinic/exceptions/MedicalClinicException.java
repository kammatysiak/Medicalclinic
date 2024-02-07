package com.kammatysiak.medicalclinic.exceptions;

import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class MedicalClinicException extends RuntimeException{

    private HttpStatus status;
    public MedicalClinicException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
