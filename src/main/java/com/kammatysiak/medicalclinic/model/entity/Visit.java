package com.kammatysiak.medicalclinic.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "VISITS")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Visit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @Column(name = "PATIENT")
    private Patient patient;
    @ManyToOne
    @Column(name = "DOCTOR")
    private Doctor doctor;
    @ManyToOne
    @Column(name = "CLINIC")
    private Clinic clinic;
    @Column(name = "VISIT_START")
    private LocalDateTime visitStart;
    @Column(name = "VISIT_END")
    private LocalDateTime visitEnd;

}
