package com.kammatysiak.medicalclinic.repository;

import com.kammatysiak.medicalclinic.model.entity.Clinic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClinicRepository extends JpaRepository<Clinic, Long> {

    Optional<Clinic> findByName(String name);

    boolean existsByName(String name);

}
