package com.kammatysiak.medicalclinic.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
@Getter
public class PatientPasswordValidationException extends RuntimeException {

    private final HttpStatus status = HttpStatus.PRECONDITION_FAILED;
}
