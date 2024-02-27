package com.kammatysiak.medicalclinic.service;

import com.kammatysiak.medicalclinic.exceptions.PatientDoesNotExistException;
import com.kammatysiak.medicalclinic.exceptions.PatientExistsException;
import com.kammatysiak.medicalclinic.exceptions.PatientNullFieldException;
import com.kammatysiak.medicalclinic.exceptions.PatientPasswordValidationException;
import com.kammatysiak.medicalclinic.mapper.PatientMapper;
import com.kammatysiak.medicalclinic.model.dto.PasswordDTO;
import com.kammatysiak.medicalclinic.model.dto.PatientCreateDTO;
import com.kammatysiak.medicalclinic.model.dto.PatientDTO;
import com.kammatysiak.medicalclinic.model.entity.Patient;
import com.kammatysiak.medicalclinic.repository.PatientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static com.kammatysiak.medicalclinic.TestDataFactory.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
        assertEquals(LocalDate.of(2022, 1, 1), result.get(1).getBirthday());
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
    void getPatient_DataIncorrect_PatientDoesNotExistExceptionThrown() {

        when(patientRepository.findByEmail(any())).thenReturn(Optional.empty());

        PatientDoesNotExistException exception = assertThrows(PatientDoesNotExistException.class,
                () -> patientService.getPatient("asd"));
        assertEquals("Patient with given e-mail does not exist.", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
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
    void createPatient_DataIncorrect_PatientExistsExceptionThrown() {
        PatientCreateDTO patientCreateDTO = createPatientCreateDTO("test@test.pl");
        when(patientRepository.existsByEmail(any())).thenReturn(true);

        PatientExistsException exception = assertThrows(PatientExistsException.class,
                () -> patientService.createPatient(patientCreateDTO));

        assertEquals("Patient with given e-mail already exists.", exception.getMessage());
        assertEquals(HttpStatus.CONFLICT, exception.getStatus());
    }

    @ParameterizedTest
    @MethodSource("validateNullsPatientCreateDTOData")
    void createPatient_DataIncorrect_PatientNullFieldExceptionThrown(PatientCreateDTO patientCreateDTO, String expectedMessage) {
        //when
        var exception = assertThrows(PatientNullFieldException.class,
                () -> patientService.createPatient(patientCreateDTO));

        //Then
        assertEquals(expectedMessage, exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }
    static Stream<Arguments> validateNullsPatientCreateDTOData() {
        return Stream.of(Arguments.of(PatientCreateDTO.builder()
                        .email("test@test.pl")
                        .lastName("Kowalski")
                        .birthday(LocalDate.of(2022, 1, 1))
                        .idCardNo("123123")
                        .password("asdzxc1")
                        .phoneNumber("606606606").build(), "Data you shared contains empty fields"),
                Arguments.of(PatientCreateDTO.builder()
                        .lastName("Kowalski")
                        .birthday(LocalDate.of(2022, 1, 1))
                        .idCardNo("123123")
                        .password("asdzxc1")
                        .phoneNumber("606606606").build(), "Data you shared contains empty fields"),
                Arguments.of(PatientCreateDTO.builder()
                        .birthday(LocalDate.of(2022, 1, 1))
                        .idCardNo("123123")
                        .password("asdzxc1")
                        .phoneNumber("606606606").build(), "Data you shared contains empty fields"),
                Arguments.of(PatientCreateDTO.builder()
                        .birthday(LocalDate.of(2022, 1, 1))
                        .idCardNo("123123")
                        .phoneNumber("606606606").build(), "Data you shared contains empty fields"),
                Arguments.of(PatientCreateDTO.builder()
                        .birthday(LocalDate.of(2022, 1, 1))
                        .idCardNo("123123")
                        .build(), "Data you shared contains empty fields"),
                Arguments.of(PatientCreateDTO.builder()
                        .birthday(LocalDate.of(2022, 1, 1))
                        .build(), "Data you shared contains empty fields"),
                Arguments.of(PatientCreateDTO.builder()
                        .build(), "Data you shared contains empty fields"));

    }

    @Test
    void deletePatient_DataCorrect_VoidReturned() {
        Optional<Patient> patient = Optional.of(createPatient("test@test.pl"));

        when(patientRepository.findByEmail(patient.get().getEmail())).thenReturn(patient);

        patientService.deletePatient(patient.get().getEmail());
        verify(patientRepository).delete(patient.get());
    }

    @Test
    void deletePatient_DataIncorrect_PatientDoesNotExistExceptionThrown() {
        //Given
        when(patientRepository.findByEmail(any())).thenReturn(Optional.empty());

        //When
        PatientDoesNotExistException exception = assertThrows(PatientDoesNotExistException.class,
                () -> patientService.deletePatient("123"));
        //Then
        assertEquals("The patient you are trying to delete does not exist", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }


    @Test
    void editPatient_DataCorrect_PatientDTOReturned() {
        Patient patient = createPatient("test@test.pl");
        PatientDTO newPatientData = createPatientDTO("test@test.pl");

        when(patientRepository.findByEmail(patient.getEmail())).thenReturn(Optional.of(patient));
        when(patientRepository.save(any())).thenReturn(patient);

        PatientDTO result = patientService.editPatient("test@test.pl", newPatientData);

        assertEquals(newPatientData.getBirthday(), result.getBirthday());
        assertEquals(newPatientData.getFirstName(), result.getFirstName());
        assertEquals(newPatientData.getLastName(), result.getLastName());
        assertEquals(newPatientData.getPhoneNumber(), result.getPhoneNumber());
        assertEquals(newPatientData.getEmail(), result.getEmail());
    }

    @ParameterizedTest
    @MethodSource("validateNullsPatientDTOData")
    void editPatient_DataIncorrect_PatientNullFieldExceptionThrown(PatientDTO patientDTO, String expectedMessage) {

        //when
        PatientNullFieldException exception = assertThrows(PatientNullFieldException.class,
                () -> patientService.editPatient("test@test.pl", patientDTO));

        //then
        assertEquals(expectedMessage, exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }

    static Stream<Arguments> validateNullsPatientDTOData() {
        return Stream.of(Arguments.of(PatientDTO.builder()
                        .email("test@test.pl")
                        .lastName("Kowalski")
                        .birthday(LocalDate.of(2022, 1, 1))
                        .phoneNumber("606606606").build(), "Provided patient contains empty fields."),
                Arguments.of(PatientDTO.builder()
                        .lastName("Kowalski")
                        .birthday(LocalDate.of(2022, 1, 1))
                        .phoneNumber("606606606").build(), "Provided patient contains empty fields."),
                Arguments.of(PatientDTO.builder()
                        .birthday(LocalDate.of(2022, 1, 1))
                        .phoneNumber("606606606").build(), "Provided patient contains empty fields."),
                Arguments.of(PatientDTO.builder()
                        .phoneNumber("606606606").build(), "Provided patient contains empty fields."),
                Arguments.of(PatientDTO.builder()
                        .build(), "Provided patient contains empty fields."));
    }

    @Test
    void editPatient_PatientNotFound_PatientDoesNotExistExceptionThrown() {
        //given
        PatientDTO patientDTO = createPatientDTO("test@test.pl");
        when(patientRepository.findByEmail(any())).thenReturn(Optional.empty());

        //when
        PatientDoesNotExistException exception = assertThrows(PatientDoesNotExistException.class,
                () -> patientService.editPatient("test@test.pl", patientDTO));

        //Then
        assertEquals("The patient you are trying to change does not exist.", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @Test
    void editPatientPassword_DataCorrect_PatientDTOReturned() {
        Patient patient = createPatient("test@test.pl");
        PasswordDTO passwordDTO = new PasswordDTO("asdzxc1", "3213");

        when(patientRepository.findByEmail(patient.getEmail())).thenReturn(Optional.of(patient));
        when(patientRepository.save(any())).thenReturn(patient);

        PatientDTO result = patientService.editPatientPassword("test@test.pl", passwordDTO);

        assertEquals("3213", patient.getPassword());
        assertEquals(patient.getBirthday(), result.getBirthday());
        assertEquals(patient.getFirstName(), result.getFirstName());
        assertEquals(patient.getLastName(), result.getLastName());
        assertEquals(patient.getPhoneNumber(), result.getPhoneNumber());
        assertEquals(patient.getEmail(), result.getEmail());
    }

    @Test
    void editPatientPassword_NoPatient_PatientDoesNotExistExceptionThrown() {
        PasswordDTO passwordDTO = new PasswordDTO("asdzxc1", "3213");
        when(patientRepository.findByEmail(any())).thenReturn(Optional.empty());

        PatientDoesNotExistException exception = assertThrows(PatientDoesNotExistException.class,
                () -> patientService.editPatientPassword("test@test.pl", passwordDTO));

        //then
        assertEquals("The patient whom you are trying to change the password does not exist.", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @Test
    void editPatientPassword_WrongPassword_PatientValidationExceptionThrown() {
        Patient patient = createPatient("test@test.pl");
        PasswordDTO passwordDTO = new PasswordDTO("asdzxc", "3213");
        when(patientRepository.findByEmail(any())).thenReturn(Optional.ofNullable(patient));

        PatientPasswordValidationException exception = assertThrows(PatientPasswordValidationException.class,
                () -> patientService.editPatientPassword("test@test.pl", passwordDTO));

        //then
        assertEquals("Incorrect password.", exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }
}
