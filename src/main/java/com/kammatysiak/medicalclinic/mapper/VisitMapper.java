package com.kammatysiak.medicalclinic.mapper;

import com.kammatysiak.medicalclinic.model.dto.VisitDTO;
import com.kammatysiak.medicalclinic.model.entity.Clinic;
import com.kammatysiak.medicalclinic.model.entity.Doctor;
import com.kammatysiak.medicalclinic.model.entity.Patient;
import com.kammatysiak.medicalclinic.model.entity.Visit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface VisitMapper {
    @Mapping(target = "patientId", source = "patient", qualifiedByName = "mapPatient")
    @Mapping(target = "doctorId", source = "doctor", qualifiedByName = "mapDoctor")
    @Mapping(target = "clinicId", source = "clinic", qualifiedByName = "mapClinic")
    VisitDTO toVisitDTO(Visit entity);

    Visit toVisit(VisitDTO entity);

    @Named("mapClinic")
    default Long mapClinics(Clinic clinic) {
        return clinic.getId();
    }

    @Named("mapPatient")
    default Long mapPatient(Patient patient) {
        return patient.getId();
    }

    @Named("mapDoctor")
    default Long mapDoctor(Doctor doctor) {
        return doctor.getId();
    }
}
