package com.kammatysiak.medicalclinic.mapper;

import com.kammatysiak.medicalclinic.model.dto.ClinicDTO;
import com.kammatysiak.medicalclinic.model.entity.Clinic;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClinicMapper {

    ClinicDTO clinicToClinicDTO(Clinic entity);

    Clinic clinicDTOToClinic(ClinicDTO entity);
}
