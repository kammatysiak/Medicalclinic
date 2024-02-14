package com.kammatysiak.medicalclinic.mapper;

import com.kammatysiak.medicalclinic.model.dto.DoctorCreateDTO;
import com.kammatysiak.medicalclinic.model.dto.DoctorDTO;
import com.kammatysiak.medicalclinic.model.entity.Doctor;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DoctorMapper {

    Doctor ToDoctor(DoctorDTO entity);

    DoctorDTO ToDoctorDTO(Doctor entity);

    Doctor ToDoctor(DoctorCreateDTO entity);
}
