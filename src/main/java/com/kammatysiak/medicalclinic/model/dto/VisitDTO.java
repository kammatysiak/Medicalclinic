package com.kammatysiak.medicalclinic.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class VisitDTO {
    private long patientId;
    private long doctorId;
    private long clinicId;
    private LocalDateTime visitStart;
    private LocalDateTime visitEnd;
}
