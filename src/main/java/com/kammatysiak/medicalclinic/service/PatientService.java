package com.kammatysiak.medicalclinic.service;

import com.kammatysiak.medicalclinic.exceptions.PatientDoesNotExistException;
import com.kammatysiak.medicalclinic.exceptions.PatientExistsException;
import com.kammatysiak.medicalclinic.mapper.PatientMapper;
import com.kammatysiak.medicalclinic.model.dto.PasswordDTO;
import com.kammatysiak.medicalclinic.model.dto.PatientCreateDTO;
import com.kammatysiak.medicalclinic.model.dto.PatientDTO;
import com.kammatysiak.medicalclinic.model.entity.Patient;
import com.kammatysiak.medicalclinic.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.kammatysiak.medicalclinic.validator.PatientValidator.*;

@RequiredArgsConstructor
@Service
public class PatientService {

    private final PatientRepository patientRepository;

    private final PatientMapper patientMapper;

    public List<PatientDTO> getPatients() {
        return patientRepository.findAll().stream()
                .map(patientMapper::patientToPatientDTO)
                .toList();
    }

    public PatientDTO getPatient(String email) {
        Patient patient = patientRepository.findByEmail(email)
                .orElseThrow(() -> new PatientDoesNotExistException("Patient with given e-mail does not exist.", HttpStatus.NOT_FOUND));
        return patientMapper.patientToPatientDTO(patient);
    }

    public PatientDTO createPatient(PatientCreateDTO patientDTO) {
        validateNullsPatient(patientMapper.patientCreateDTOToPatient(patientDTO), "Data you shared contains empty fields");
        validateIfPatientAlreadyExists(patientRepository.existsByEmail(patientDTO.getEmail()), "Patient with given e-mail already exists.");
        Patient patient = patientMapper.patientCreateDTOToPatient(patientDTO);
        return patientMapper.patientToPatientDTO(patientRepository.save(patient));
    }

    public void deletePatient(String email) {
        Patient patient = patientRepository.findByEmail(email)
                .orElseThrow(() -> new PatientDoesNotExistException("The patient you are trying to delete does not exist", HttpStatus.NOT_FOUND));
        patientRepository.delete(patient);
    }

    public PatientDTO editPatient(String email, PatientDTO newPatientData) {
        validateNullsPatientDTO(newPatientData, "Provided patient contains empty fields.");
        Patient patient = patientRepository.findByEmail(email)
                .orElseThrow(() -> new PatientDoesNotExistException("The patient you are trying to change does not exist.", HttpStatus.NOT_FOUND));
        patient.setFirstName(newPatientData.getFirstName());
        patient.setBirthday(newPatientData.getBirthday());
        patient.setBirthday(newPatientData.getBirthday());
        patient.setLastName(newPatientData.getLastName());
        patient.setPhoneNumber(newPatientData.getPhoneNumber());
        isEmailAvailable(patient.getEmail(), newPatientData.getEmail());
        patient.setEmail(newPatientData.getEmail());
        return patientMapper.patientToPatientDTO(patientRepository.save(patient));
    }

    public PatientDTO editPatientPassword(String email, PasswordDTO passwordsDTO) {
        Patient patient = patientRepository.findByEmail(email)
                .orElseThrow(() -> new PatientDoesNotExistException("The patient whom you are trying to change the password does not exist.", HttpStatus.NOT_FOUND));
        validatePasswordChange(passwordsDTO, patient, "Incorrect password.");
        patient.setPassword(passwordsDTO.getNewPassword());
        return patientMapper.patientToPatientDTO(patientRepository.save(patient));
    }


    private void isEmailAvailable(String currentEmail, String newEmail) {
        if (!newEmail.equals(currentEmail)) {
            if (patientRepository.existsByEmail(newEmail)) {
                throw new PatientExistsException("Email already exists for " + newEmail, HttpStatus.CONFLICT);
            }
        }
    }
}
