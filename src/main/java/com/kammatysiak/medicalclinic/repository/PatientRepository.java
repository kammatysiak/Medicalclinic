package com.kammatysiak.medicalclinic.repository;

import com.kammatysiak.medicalclinic.model.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient, String> {

    Optional<Patient> findByEmail(String email);

    boolean existsByEmail(String email);

}
