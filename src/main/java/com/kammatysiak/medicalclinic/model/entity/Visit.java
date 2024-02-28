package com.kammatysiak.medicalclinic.model.entity;

import com.kammatysiak.medicalclinic.model.dto.VisitDTO;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "VISITS")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
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


    public static void setVisitData(Visit visit, Clinic clinic, Doctor doctor, VisitDTO visitDTO) {
        visit.setClinic(clinic);
        visit.setDoctor(doctor);
        visit.setVisitStart(visitDTO.getVisitStart());
        visit.setVisitEnd(visitDTO.getVisitEnd());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Visit visit = (Visit) o;
        return Objects.equals(id, visit.id) && id != null;
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "Visit{" +
                "id=" + id +
                ", patient=" + patient.getId() +
                ", doctor=" + doctor.getId() +
                ", clinic=" + clinic.getId() +
                ", visitStart=" + visitStart +
                ", visitEnd=" + visitEnd +
                '}';
    }
}
