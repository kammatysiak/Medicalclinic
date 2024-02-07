package com.kammatysiak.medicalclinic.service;

import com.kammatysiak.medicalclinic.exceptions.PatientDoesNotExistException;
import com.kammatysiak.medicalclinic.exceptions.PatientExistsException;
import com.kammatysiak.medicalclinic.exceptions.PatientNullFieldException;
import com.kammatysiak.medicalclinic.mapper.PatientMapper;
import com.kammatysiak.medicalclinic.model.dto.PasswordClassDTO;
import com.kammatysiak.medicalclinic.model.dto.PatientCreateDTO;
import com.kammatysiak.medicalclinic.model.dto.PatientDTO;
import com.kammatysiak.medicalclinic.model.entity.Patient;
import com.kammatysiak.medicalclinic.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.kammatysiak.medicalclinic.validator.PatientValidator.*;

@RequiredArgsConstructor
@Service
public class PatientService {

    private final PatientRepository patientRepository;

    private final PatientMapper patientMapper;

    public List<PatientDTO> getPatients() {
        return patientRepository.findAll().stream()
                .map(patientMapper::patientToPatientDTO)
                .collect(Collectors.toList());
    }

    public PatientDTO getPatient(String email) {
        validateIfPatientIsNull(patientRepository.existsById(email), "Patient with given e-mail does not exist.");
        return patientMapper.patientToPatientDTO(patientRepository.getById(email));
    }

    public PatientDTO createPatient(PatientCreateDTO patientDTO) {
        validateNullsPatient(patientMapper.patientCreateDTOToPatient(patientDTO), "Data you shared contains empty fields");
        validateIfPatientAlreadyExists(patientRepository.existsById(patientDTO.getEmail()), "Patient with given e-mail already exists.");
       Patient patient = patientMapper.patientCreateDTOToPatient(patientDTO);
        return patientMapper.patientToPatientDTO(patientRepository.save(patient));
    }

    public void deletePatient(String email) {
        validateIfPatientIsNull(patientRepository.existsById(email), "The patient you are trying to delete does not exist");
        patientRepository.deleteById(email);
    }

    public PatientDTO editPatient(String email, PatientDTO newPatientData) {
        validateNullsPatientDTO(newPatientData, "Provided patient contains empty fields.");
        validateIfPatientIsNull(patientRepository.existsById(email), "The patient you are trying to change does not exist.");
        Patient patient = patientRepository.getById(email);
        Patient patientupdate = patientMapper.patientDTOToPatient(newPatientData);
        patientupdate.setIdCardNo(patient.getIdCardNo());
        patientupdate.setPassword(patient.getPassword());
        patientupdate.setModifyDate(patient.getModifyDate());
        return patientMapper.patientToPatientDTO(patientRepository.save(patientupdate));
    }

    public PatientDTO editPatientPassword(String email, PasswordClassDTO passwordsDTO) {
        Patient patient = patientRepository.getById(email);
        validateIfPatientIsNull(patientRepository.existsById(email), "The patient whom you are trying to change the password does not exist.");
        validatePasswordChange(passwordsDTO, patient, "Incorrect password.");
        patient.setPassword(passwordsDTO.getNewPassword());
        return patientMapper.patientToPatientDTO(patientRepository.save(patient));
    }
}