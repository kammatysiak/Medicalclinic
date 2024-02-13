package com.kammatysiak.medicalclinic.model.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "CLINICS")
@Entity
public class Clinic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "NAME")
    private String name;
    @Column(name = "CITY")
    private String city;
    @Column(name = "POSTCODE")
    private String postCode;
    @Column(name = "STREET")
    private String street;
    @Column(name = "BUILDINGNUMBER")
    private String buildingNumber;

    @ManyToMany(mappedBy = "employmentsInClinics")
    Set<Doctor> doctorsInClinic;
}
