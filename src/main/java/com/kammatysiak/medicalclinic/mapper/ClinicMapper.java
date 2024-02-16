package com.kammatysiak.medicalclinic.mapper;

import com.kammatysiak.medicalclinic.model.dto.ClinicDTO;
import com.kammatysiak.medicalclinic.model.entity.Clinic;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ClinicMapper {
    @Mapping(target = "doctorIds", expression = "java(entity.getDoctors().stream().map(d -> d.getId()).toList();)")
    ClinicDTO ToClinicDTO(Clinic entity);

    Clinic ToClinic(ClinicDTO entity);
}
