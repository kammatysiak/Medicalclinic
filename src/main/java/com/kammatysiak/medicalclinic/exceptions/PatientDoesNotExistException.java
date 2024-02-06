package com.kammatysiak.medicalclinic.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
@Getter
public class PatientDoesNotExistException extends RuntimeException{

    private final HttpStatus status = HttpStatus.NOT_FOUND;
}
