package com.kammatysiak.medicalclinic.mapper;

import com.kammatysiak.medicalclinic.model.dto.DoctorCreateDTO;
import com.kammatysiak.medicalclinic.model.dto.DoctorDTO;
import com.kammatysiak.medicalclinic.model.entity.Clinic;
import com.kammatysiak.medicalclinic.model.entity.Doctor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface DoctorMapper {


    Doctor todoctor(DoctorDTO entity);

    @Mapping(target = "clinicIds", source ="clinics", qualifiedByName="mapClinics")
    DoctorDTO toDoctorDTO(Doctor entity);
    Doctor todoctor(DoctorCreateDTO entity);

    @Named("mapClinics")
    default List<Long> mapClinics(Set<Clinic> clinicsSet){
        return clinicsSet.stream()
                .map(Clinic::getId)
                .collect(Collectors.toList());
    }
}
