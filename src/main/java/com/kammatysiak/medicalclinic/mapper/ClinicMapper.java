package com.kammatysiak.medicalclinic.mapper;

import com.kammatysiak.medicalclinic.model.dto.ClinicDTO;
import com.kammatysiak.medicalclinic.model.entity.Clinic;
import com.kammatysiak.medicalclinic.model.entity.Doctor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ClinicMapper {

    @Mapping(target = "doctorIds", source = "doctors", qualifiedByName = "mapDoctors")
    ClinicDTO toClinicDTO(Clinic entity);

    Clinic toClinic(ClinicDTO entity);

    @Named("mapDoctors")
    default List<Long> mapDoctors(Set<Doctor> doctorsSet) {
        if (doctorsSet == null) {
            return new ArrayList<>();
        }
        return doctorsSet.stream()
                .map(Doctor::getId)
                .collect(Collectors.toList());
    }
}
