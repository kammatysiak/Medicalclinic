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

import static com.kammatysiak.medicalclinic.validator.PatientValidator.*;

@RequiredArgsConstructor
@Service
public class PatientService {

    private final PatientRepository patientRepository;

    private final PatientMapper patientMapper;

    public List<PatientDTO> getPatients() {
        List<Patient> patientList = patientRepository.findAll();
        List<PatientDTO> patientDTOList = new ArrayList<>();
        for (Patient patient : patientList) {
            patientDTOList.add(patientMapper.patientToPatientDTO(patient));
        }
        return patientDTOList;
    }

    public PatientDTO getPatient(String email) {
        return patientMapper.patientToPatientDTO(patientRepository.findById(email).orElseThrow(PatientDoesNotExistException::new));
    }

    public PatientDTO createPatient(PatientCreateDTO patientDTO) {
        if (validateNullsPatient(patientMapper.patientCreateDTOToPatient(patientDTO))) {
            throw new PatientNullFieldException();
        }
        if (patientRepository.existsById((patientMapper.patientCreateDTOToPatient(patientDTO).getEmail()))) {
            throw new PatientExistsException();
        }
        return patientMapper.patientToPatientDTO(patientRepository.save(patientMapper.patientCreateDTOToPatient(patientDTO)));
    }

    public void deletePatient(String email) {
        if (!patientRepository.existsById(email)) {
            throw new PatientDoesNotExistException();
        }
        patientRepository.deleteById(email);
    }

    public PatientDTO editPatient(String email, PatientDTO newPatientData) {
        if (validateNullsPatientDTO(newPatientData)) {
            throw new PatientNullFieldException();
        }
        Patient patient = patientRepository.getById(email);
        if (patient.equals(null)) {
            throw new PatientDoesNotExistException();
        }
        Patient patientupdate = patientMapper.patientDTOToPatient(newPatientData);
        patientupdate.setIdCardNo(patient.getIdCardNo());
        patientupdate.setPassword(patient.getPassword());
        patientupdate.setModifyDate(patient.getModifyDate());
        return patientMapper.patientToPatientDTO(patientRepository.save(patientupdate));
    }

    public PatientDTO editPatientPassword(String email, PasswordClassDTO passwordsDTO) {
        Patient patient = patientRepository.getById(email);
        if (patient.equals(null)) {
            throw new PatientDoesNotExistException();
        }
        validatePasswordChange(passwordsDTO, patient);
        patient.setPassword(passwordsDTO.getNewPassword());
        return patientMapper.patientToPatientDTO(patientRepository.save(patient));
    }
}