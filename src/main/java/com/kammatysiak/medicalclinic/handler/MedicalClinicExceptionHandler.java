package com.kammatysiak.medicalclinic.handler;

import com.kammatysiak.medicalclinic.exceptions.PatientDoesNotExistException;
import com.kammatysiak.medicalclinic.exceptions.PatientExistsException;
import com.kammatysiak.medicalclinic.exceptions.PatientNullFieldException;
import com.kammatysiak.medicalclinic.exceptions.PatientPasswordValidationException;
import com.kammatysiak.medicalclinic.model.dto.ExceptionMessageDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class MedicalClinicExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({PatientNullFieldException.class})
    public ResponseEntity<ExceptionMessageDTO> handlePatientNullFieldException(PatientNullFieldException ex) {
        return ResponseEntity.status(ex.getStatus()).body(new ExceptionMessageDTO(ex.getMessage(), LocalDateTime.now(), ex.getStatus()));
    }

    @ExceptionHandler({PatientExistsException.class})
    public ResponseEntity<ExceptionMessageDTO> handlePatientExistsException(PatientExistsException ex) {
        return ResponseEntity.status(ex.getStatus()).body(new ExceptionMessageDTO(ex.getMessage(), LocalDateTime.now(), ex.getStatus()));
    }

    @ExceptionHandler({PatientDoesNotExistException.class})
    public ResponseEntity<ExceptionMessageDTO> handlePatientDoesNotExistException(PatientDoesNotExistException ex) {
        return ResponseEntity.status(ex.getStatus()).body(new ExceptionMessageDTO(ex.getMessage(), LocalDateTime.now(), ex.getStatus()));
    }

    @ExceptionHandler({PatientPasswordValidationException.class})
    public ResponseEntity<ExceptionMessageDTO> handlePatientPasswordValidationException(PatientPasswordValidationException ex) {
        return ResponseEntity.status(ex.getStatus()).body(new ExceptionMessageDTO(ex.getMessage(), LocalDateTime.now(), ex.getStatus()));
    }

}
