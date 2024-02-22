package com.kammatysiak.medicalclinic.service;

import com.kammatysiak.medicalclinic.mapper.PatientMapper;
import com.kammatysiak.medicalclinic.model.dto.PasswordDTO;
import com.kammatysiak.medicalclinic.model.dto.PatientCreateDTO;
import com.kammatysiak.medicalclinic.model.dto.PatientDTO;
import com.kammatysiak.medicalclinic.model.entity.Patient;
import com.kammatysiak.medicalclinic.repository.PatientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.kammatysiak.medicalclinic.TestDataFactory.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PatientServiceTests {

    PatientService patientService;
    PatientRepository patientRepository;
    PatientMapper patientMapper;

    @BeforeEach
    void setup() {
        this.patientRepository = Mockito.mock(PatientRepository.class);
        this.patientMapper = Mappers.getMapper(PatientMapper.class);
        this.patientService = new PatientService(patientRepository, patientMapper);
    }

    @Test
    void getPatients_DataCorrect_PatientsDTOReturned() {

        Patient patient = createPatient("test@test.pl");
        Patient patient1 = createPatient("test1@test.pl");

        List<Patient> patients = new ArrayList<>();
        patients.add(patient);
        patients.add(patient1);

        when(patientRepository.findAll()).thenReturn(patients);

        List<PatientDTO> result = patientService.getPatients();

        assertEquals("test@test.pl", result.get(0).getEmail());
        assertEquals("Tomasz", result.get(0).getFirstName());
        assertEquals("Kowalski", result.get(0).getLastName());
        assertEquals("606606606", result.get(0).getPhoneNumber());
        assertEquals(LocalDate.of(2022, 1, 1), result.get(0).getBirthday());
        assertEquals("test1@test.pl", result.get(1).getEmail());
        assertEquals("Tomasz", result.get(1).getFirstName());
        assertEquals("Kowalski", result.get(1).getLastName());
        assertEquals("606606606", result.get(1).getPhoneNumber());
        assertEquals(LocalDate.of(2021, 2, 1), result.get(1).getBirthday());

    }

    @Test
    void getPatient_DataCorrect_PatientDTOReturned() {

        Patient patient = createPatient("test@test.pl");


        when(patientRepository.findByEmail(patient.getEmail())).thenReturn(Optional.of(patient));

        PatientDTO result = patientService.getPatient("test@test.pl");

        assertEquals("test@test.pl", result.getEmail());
        assertEquals("Tomasz", result.getFirstName());
        assertEquals("Kowalski", result.getLastName());
        assertEquals("606606606", result.getPhoneNumber());
        assertEquals(LocalDate.of(2022, 1, 1), result.getBirthday());

    }

    @Test
    void createPatient_DataCorrect_PatientDTOReturned() {

        PatientCreateDTO patientCreateDTO = createPatientCreateDTO("test@test.pl");
        Patient patient = createPatient("test@test.pl");

        when(patientRepository.existsByEmail(patientCreateDTO.getEmail())).thenReturn(false);
        when(patientRepository.save(any())).thenReturn(patient);

        PatientDTO result = patientService.createPatient(patientCreateDTO);

        assertEquals(patientCreateDTO.getEmail(), result.getEmail());
        assertEquals(patientCreateDTO.getFirstName(), result.getFirstName());
        assertEquals(patientCreateDTO.getLastName(), result.getLastName());
        assertEquals(patientCreateDTO.getPhoneNumber(), result.getPhoneNumber());
        assertEquals(patientCreateDTO.getBirthday(), result.getBirthday());

    }
    @Test
    void deletePatient_DataCorrect_VoidReturned(){
        Optional<Patient> patient = Optional.of(createPatient("test@test.pl"));

        when(patientRepository.findByEmail(patient.get().getEmail())).thenReturn(patient);

        patientService.deletePatient(patient.get().getEmail());
        verify(patientRepository).delete(patient.get());
    }

    @Test
    void editPatient_DataCorrect_PatientDTOReturned(){
        Patient patient = createPatient("test@test.pl");
        PatientDTO newPatientData = createPatientDTO("test@test.pl");

        when(patientRepository.findByEmail(patient.getEmail())).thenReturn(Optional.of(patient));
        when(patientRepository.save(any())).thenReturn(patient);

        PatientDTO result = patientService.editPatient("test@test.pl", newPatientData);

        assertEquals(newPatientData.getBirthday(),result.getBirthday());
        assertEquals(newPatientData.getFirstName(),result.getFirstName());
        assertEquals(newPatientData.getLastName(),result.getLastName());
        assertEquals(newPatientData.getPhoneNumber(),result.getPhoneNumber());
        assertEquals(newPatientData.getEmail(),result.getEmail());
    }
    @Test
    void editPatientPassword_DataCorrect_PatientDTOReturned(){
        Patient patient = createPatient("test@test.pl");
        PasswordDTO passwordDTO = new PasswordDTO("asdzxc1", "3213");

        when(patientRepository.findByEmail(patient.getEmail())).thenReturn(Optional.of(patient));
        when(patientRepository.save(any())).thenReturn(patient);

        PatientDTO result = patientService.editPatientPassword("test@test.pl", passwordDTO);

        assertEquals("3213", patient.getPassword());
        assertEquals(patient.getBirthday(),result.getBirthday());
        assertEquals(patient.getFirstName(),result.getFirstName());
        assertEquals(patient.getLastName(),result.getLastName());
        assertEquals(patient.getPhoneNumber(),result.getPhoneNumber());
        assertEquals(patient.getEmail(),result.getEmail());
    }


}
