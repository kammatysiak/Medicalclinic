package com.kammatysiak.medicalclinic.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "VISITS")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Visit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "PATIENT_ID")
    private Patient patient;
    @ManyToOne
    @JoinColumn(name = "DOCTOR_ID")
    private Doctor doctor;
    @ManyToOne
    @JoinColumn(name = "CLINIC_ID")
    private Clinic clinic;
    @Column(name = "VISIT_START")
    private LocalDateTime visitStart;
    @Column(name = "VISIT_END")
    private LocalDateTime visitEnd;

}
