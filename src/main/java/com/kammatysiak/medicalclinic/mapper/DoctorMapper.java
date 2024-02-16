package com.kammatysiak.medicalclinic.mapper;

import com.kammatysiak.medicalclinic.model.dto.DoctorCreateDTO;
import com.kammatysiak.medicalclinic.model.dto.DoctorDTO;
import com.kammatysiak.medicalclinic.model.entity.Doctor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DoctorMapper {


    Doctor ToDoctor(DoctorDTO entity);

    @Mapping(target = "clinicIds", expression = "java(entity.getClinics().stream().map(c -> c.getId()).toList();)")
    DoctorDTO ToDoctorDTO(Doctor entity);

    Doctor ToDoctor(DoctorCreateDTO entity);
}
