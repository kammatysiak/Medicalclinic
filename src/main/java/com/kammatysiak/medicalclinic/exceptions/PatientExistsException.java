package com.kammatysiak.medicalclinic.exceptions;

import lombok.Data;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class PatientExistsException extends RuntimeException{

    private final HttpStatus status = HttpStatus.CONFLICT;


}
