package com.kammatysiak.medicalclinic.repository;

import com.kammatysiak.medicalclinic.model.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, String> {

}
