package com.kammatysiak.medicalclinic.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ExceptionMessageDTO {

    private String message;
    private LocalDateTime exceptionTime;
    private HttpStatus status;

}
