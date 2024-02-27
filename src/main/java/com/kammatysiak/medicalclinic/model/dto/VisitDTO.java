package com.kammatysiak.medicalclinic.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Builder
public class VisitDTO {
    private long patientId;
    private long doctorId;
    private long clinicId;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime visitStart;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime visitEnd;
}
