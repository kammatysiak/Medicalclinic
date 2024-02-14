package com.kammatysiak.medicalclinic.service;

import com.kammatysiak.medicalclinic.exceptions.ClinicDoesNotExistException;
import com.kammatysiak.medicalclinic.mapper.ClinicMapper;
import com.kammatysiak.medicalclinic.model.dto.ClinicDTO;
import com.kammatysiak.medicalclinic.model.entity.Clinic;
import com.kammatysiak.medicalclinic.repository.ClinicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.kammatysiak.medicalclinic.validator.ClinicValidator.validateIfClinicAlreadyExists;
import static com.kammatysiak.medicalclinic.validator.ClinicValidator.validateNullsClinic;

@RequiredArgsConstructor
@Service
public class ClinicService {
    private final ClinicRepository clinicRepository;

    private final ClinicMapper clinicMapper;

    public List<ClinicDTO> getClinics() {
        return clinicRepository.findAll().stream()
                .map(clinicMapper::ToClinicDTO)
                .toList();
    }

    public ClinicDTO getClinic(String name) {
        Clinic clinic = clinicRepository.findByName(name)
                .orElseThrow(() -> new ClinicDoesNotExistException("Clinic with given name does not exist.", HttpStatus.NOT_FOUND));
        return clinicMapper.ToClinicDTO(clinic);
    }

    public ClinicDTO createClinic(ClinicDTO clinicDTO) {
        validateNullsClinic(clinicMapper.ToClinic(clinicDTO), "Data you shared contains empty fields");
        validateIfClinicAlreadyExists(clinicRepository.existsByName(clinicDTO.getName()), "Clinic with given name already exists.");
        Clinic clinic = clinicMapper.ToClinic(clinicDTO);
        return clinicMapper.ToClinicDTO(clinicRepository.save(clinic));
    }

    public void deleteClinic(String name) {
        Clinic clinic = clinicRepository.findByName(name)
                .orElseThrow(() -> new ClinicDoesNotExistException("The clinic you are trying to delete does not exist", HttpStatus.CONFLICT));
        clinicRepository.delete(clinic);
    }

}
