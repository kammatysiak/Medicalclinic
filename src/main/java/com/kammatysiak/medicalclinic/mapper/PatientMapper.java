package com.kammatysiak.medicalclinic.mapper;

import com.kammatysiak.medicalclinic.model.dto.PasswordClassDTO;
import com.kammatysiak.medicalclinic.model.dto.PatientCreateDTO;
import com.kammatysiak.medicalclinic.model.dto.PatientDTO;
import com.kammatysiak.medicalclinic.model.entity.Patient;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PatientMapper {

    PatientDTO patientToPatientDTO(Patient entity);


    Patient patientDTOToPatient(PatientDTO dto);


    Patient patientCreateDTOToPatient(PatientCreateDTO dto);

    Patient patientToPatient(Patient patient);

}
