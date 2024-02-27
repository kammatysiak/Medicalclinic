package com.kammatysiak.medicalclinic.repository;

import com.kammatysiak.medicalclinic.model.entity.Visit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface VisitRepository extends JpaRepository<Visit, Long> {
    Optional<Visit> findById(Long id);

    @Query("select v " +
            "from Visit v " +
            "where :doctorId = v.doctor.id " +
            "and v.visitStart  <= :endDateTime " +
            "and (v.visitEnd) >= :startDateTime")
    List<Visit> findAllOverlapping(long doctorId, LocalDateTime startDateTime, LocalDateTime endDateTime);
}
