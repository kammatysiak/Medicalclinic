package com.kammatysiak.medicalclinic.mapper;

import com.kammatysiak.medicalclinic.model.dto.DoctorDTO;
import com.kammatysiak.medicalclinic.model.entity.Doctor;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DoctorMapper {

    Doctor doctorDTOToDoctor(DoctorDTO entity);

    DoctorDTO doctorToDoctorDTO(Doctor entity);
}
