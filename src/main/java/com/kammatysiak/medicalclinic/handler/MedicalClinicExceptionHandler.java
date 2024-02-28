package com.kammatysiak.medicalclinic.handler;

import com.kammatysiak.medicalclinic.exceptions.*;
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

    @ExceptionHandler({ClinicDoesNotExistException.class})
    public ResponseEntity<ExceptionMessageDTO> handleClinicDoesNotExistException(ClinicDoesNotExistException ex) {
        return ResponseEntity.status(ex.getStatus()).body(new ExceptionMessageDTO(ex.getMessage(), LocalDateTime.now(), ex.getStatus()));
    }

    @ExceptionHandler({ClinicExistsException.class})
    public ResponseEntity<ExceptionMessageDTO> handleClinicExistsException(ClinicExistsException ex) {
        return ResponseEntity.status(ex.getStatus()).body(new ExceptionMessageDTO(ex.getMessage(), LocalDateTime.now(), ex.getStatus()));
    }

    @ExceptionHandler({ClinicNullFieldException.class})
    public ResponseEntity<ExceptionMessageDTO> handleClinicNullFieldException(ClinicNullFieldException ex) {
        return ResponseEntity.status(ex.getStatus()).body(new ExceptionMessageDTO(ex.getMessage(), LocalDateTime.now(), ex.getStatus()));
    }

    @ExceptionHandler({DoctorNullFieldException.class})
    public ResponseEntity<ExceptionMessageDTO> handleDoctorNullFieldException(DoctorNullFieldException ex) {
        return ResponseEntity.status(ex.getStatus()).body(new ExceptionMessageDTO(ex.getMessage(), LocalDateTime.now(), ex.getStatus()));
    }

    @ExceptionHandler({DoctorDoesNotExistException.class})
    public ResponseEntity<ExceptionMessageDTO> handleDoctorDoesNotExistException(DoctorDoesNotExistException ex) {
        return ResponseEntity.status(ex.getStatus()).body(new ExceptionMessageDTO(ex.getMessage(), LocalDateTime.now(), ex.getStatus()));
    }

    @ExceptionHandler({DoctorExistsException.class})
    public ResponseEntity<ExceptionMessageDTO> handleDoctorExistsException(DoctorExistsException ex) {
        return ResponseEntity.status(ex.getStatus()).body(new ExceptionMessageDTO(ex.getMessage(), LocalDateTime.now(), ex.getStatus()));
    }

    @ExceptionHandler({VisitNullFieldException.class})
    public ResponseEntity<ExceptionMessageDTO> handleVisitNullFieldException(VisitNullFieldException ex) {
        return ResponseEntity.status(ex.getStatus()).body(new ExceptionMessageDTO(ex.getMessage(), LocalDateTime.now(), ex.getStatus()));
    }

    @ExceptionHandler({VisitTimingException.class})
    public ResponseEntity<ExceptionMessageDTO> handleVisitTimingException(VisitTimingException ex) {
        return ResponseEntity.status(ex.getStatus()).body(new ExceptionMessageDTO(ex.getMessage(), LocalDateTime.now(), ex.getStatus()));
    }
    @ExceptionHandler({VisitDoesNotExistException.class})
    public ResponseEntity<ExceptionMessageDTO> handleVisitDoesNotExistException(VisitDoesNotExistException ex) {
        return ResponseEntity.status(ex.getStatus()).body(new ExceptionMessageDTO(ex.getMessage(), LocalDateTime.now(), ex.getStatus()));
    }
}
