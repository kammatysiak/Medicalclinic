package com.kammatysiak.medicalclinic.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "DOCTORS")
@Entity
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "FIRST_NAME")
    private String firstName;
    @Column(name = "LAST_NAME")
    private String lastName;
    @Column(name = "SPECIALIZATION")
    private String specialization;
    @Column(name = "MODIFY_DATE")
    private LocalDateTime modifyDate;

    @ManyToMany
    @JoinTable(
            name = "DoctorEmploymentInClinics",
            joinColumns = @JoinColumn(name = "doctor_id"),
            inverseJoinColumns = @JoinColumn(name = "clinic_id"))
    Set<Clinic> employmentsInClinics;

}
