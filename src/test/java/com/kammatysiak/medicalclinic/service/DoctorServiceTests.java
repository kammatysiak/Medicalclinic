package com.kammatysiak.medicalclinic.service;

import com.kammatysiak.medicalclinic.exceptions.ClinicDoesNotExistException;
import com.kammatysiak.medicalclinic.exceptions.DoctorDoesNotExistException;
import com.kammatysiak.medicalclinic.exceptions.DoctorExistsException;
import com.kammatysiak.medicalclinic.exceptions.DoctorNullFieldException;
import com.kammatysiak.medicalclinic.mapper.DoctorMapper;
import com.kammatysiak.medicalclinic.model.dto.DoctorCreateDTO;
import com.kammatysiak.medicalclinic.model.dto.DoctorDTO;
import com.kammatysiak.medicalclinic.model.dto.EmploymentDTO;
import com.kammatysiak.medicalclinic.model.entity.Clinic;
import com.kammatysiak.medicalclinic.model.entity.Doctor;
import com.kammatysiak.medicalclinic.repository.ClinicRepository;
import com.kammatysiak.medicalclinic.repository.DoctorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static com.kammatysiak.medicalclinic.TestDataFactory.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class DoctorServiceTests {
    DoctorService doctorService;
    DoctorRepository doctorRepository;
    ClinicRepository clinicRepository;
    DoctorMapper doctorMapper;

    @BeforeEach
    void setup() {
        this.doctorRepository = Mockito.mock(DoctorRepository.class);
        this.doctorMapper = Mappers.getMapper(DoctorMapper.class);
        this.clinicRepository = Mockito.mock(ClinicRepository.class);
        this.doctorService = new DoctorService(doctorRepository, clinicRepository, doctorMapper);
    }

    @Test
    void getDoctors_DataCorrect_ListDoctorDTOReturned() {
        Doctor doctor = createDoctor("test@test.pl");
        Doctor doctor1 = createDoctor("test1@test.pl");

        List<Doctor> doctors = new ArrayList<>();
        doctors.add(doctor);
        doctors.add(doctor1);

        when(doctorRepository.findAll()).thenReturn(doctors);

        List<DoctorDTO> result = doctorService.getDoctors();

        assertEquals("test@test.pl", result.get(0).getEmail());
        assertEquals("Tomasz", result.get(0).getFirstName());
        assertEquals("Kowalski", result.get(0).getLastName());
        assertEquals("Oncology", result.get(0).getSpecialization());
        assertTrue(result.get(0).getClinicIds().isEmpty());
        assertEquals("test1@test.pl", result.get(1).getEmail());
        assertEquals("Tomasz", result.get(1).getFirstName());
        assertEquals("Kowalski", result.get(1).getLastName());
        assertEquals("Oncology", result.get(1).getSpecialization());
        assertTrue(result.get(1).getClinicIds().isEmpty());
    }

    @Test
    void getDoctor_DataCorrect_DoctorDTOReturned() {

        Doctor doctor = createDoctor("test@test.pl");

        when(doctorRepository.findByEmail(doctor.getEmail())).thenReturn(Optional.of(doctor));

        DoctorDTO result = doctorService.getDoctor("test@test.pl");

        assertEquals("test@test.pl", result.getEmail());
        assertEquals("Tomasz", result.getFirstName());
        assertEquals("Kowalski", result.getLastName());
        assertEquals("Oncology", result.getSpecialization());
        assertTrue(result.getClinicIds().isEmpty());

    }

    @Test
    void getDoctor_DataIncorrect_DoctorDoesNotExistExceptionThrown() {

        when(doctorRepository.findByEmail(any())).thenReturn(Optional.empty());

        DoctorDoesNotExistException exception = assertThrows(DoctorDoesNotExistException.class,
                () -> doctorService.getDoctor("asd"));
        assertEquals("Doctor with given email does not exist.", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }


    @Test
    void createDoctor_DataCorrect_DoctorDTOReturned() {

        DoctorCreateDTO doctorCreateDTO = createDoctorCreateDTO("test@test.pl");
        Doctor doctor = createDoctor("test@test.pl");

        when(doctorRepository.existsByEmail(doctorCreateDTO.getEmail())).thenReturn(false);
        when(doctorRepository.save(any())).thenReturn(doctor);

        DoctorDTO result = doctorService.createDoctor(doctorCreateDTO);

        assertEquals(doctorCreateDTO.getEmail(), result.getEmail());
        assertEquals(doctorCreateDTO.getFirstName(), result.getFirstName());
        assertEquals(doctorCreateDTO.getLastName(), result.getLastName());
        assertEquals(doctorCreateDTO.getSpecialization(), result.getSpecialization());
        assertEquals(doctorCreateDTO.getClinicIds(), result.getClinicIds());
    }

    @ParameterizedTest
    @MethodSource("validateNullsDoctorCreateDTOData")
    void createDoctor_DataIncorrect_DoctorNullFieldExceptionThrown(DoctorCreateDTO doctorCreateDTO, String expectedMessage) {
        //when
        var exception = assertThrows(DoctorNullFieldException.class,
                () -> doctorService.createDoctor(doctorCreateDTO));

        //Then
        assertEquals(expectedMessage, exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }

    static Stream<Arguments> validateNullsDoctorCreateDTOData() {
        return Stream.of(Arguments.of(DoctorCreateDTO.builder()
                        .lastName("Kowalski")
                        .password("asdzxc1")
                        .specialization("Oncology").email("test@test.pl")
                        .build(), "Data you shared contains empty fields"),
                Arguments.of(DoctorCreateDTO.builder()
                        .password("asdzxc1")
                        .specialization("Oncology").email("test@test.pl")
                        .build(), "Data you shared contains empty fields"),
                Arguments.of(DoctorCreateDTO.builder()
                        .specialization("Oncology").email("test@test.pl")
                        .build(), "Data you shared contains empty fields"),
                Arguments.of(DoctorCreateDTO.builder()
                        .build(), "Data you shared contains empty fields"));
    }

    @Test
    void createDoctor_DataIncorrect_DoctorExistsExceptionThrown() {
        DoctorCreateDTO doctorCreateDTO = createDoctorCreateDTO("test@test.pl");
        when(doctorRepository.existsByEmail(any())).thenReturn(true);

        DoctorExistsException exception = assertThrows(DoctorExistsException.class,
                () -> doctorService.createDoctor(doctorCreateDTO));
        assertEquals("Doctor with given email already exists.", exception.getMessage());
        assertEquals(HttpStatus.CONFLICT, exception.getStatus());
    }

    @Test
    void deleteDoctor_DataCorrect_VoidReturned() {
        Doctor doctor = createDoctor("test@test.pl");

        when(doctorRepository.findByEmail(doctor.getEmail())).thenReturn(Optional.of(doctor));

        doctorService.deleteDoctor(doctor.getEmail());

        verify(doctorRepository).delete(doctor);
    }

    @Test
    void deleteDoctor_DataIncorrect_DoctorDoesNotExistExceptionThrown() {
        //Given
        when(doctorRepository.findByEmail(any())).thenReturn(Optional.empty());

        //When
        DoctorDoesNotExistException exception = assertThrows(DoctorDoesNotExistException.class,
                () -> doctorService.deleteDoctor("123"));
        //Then
        assertEquals("The doctor you are trying to delete does not exist", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @Test
    void employDoctorInClinic_DataCorrect_VoidReturned() {
        Doctor doctor = createDoctor("test@test.pl");
        Clinic clinic = createClinic("Medicover");
        EmploymentDTO employmentDTO = new EmploymentDTO(doctor.getEmail(), clinic.getName());

        when(doctorRepository.findByEmail(doctor.getEmail())).thenReturn(Optional.of(doctor));
        when(clinicRepository.findByName(clinic.getName())).thenReturn(Optional.of(clinic));
        when(doctorRepository.save(any())).thenReturn(doctor);

        DoctorDTO result = doctorService.employDoctorInClinic(doctor.getEmail(), employmentDTO);

        assertEquals(doctor.getEmail(), result.getEmail());
        assertEquals(doctor.getFirstName(), result.getFirstName());
        assertEquals(doctor.getLastName(), result.getLastName());
        assertEquals(doctor.getSpecialization(), result.getSpecialization());
        assertEquals(doctor.getClinics().stream()
                .map(Clinic::getId)
                .toList(), result.getClinicIds());
    }

    @Test
    void employDoctorInClinic_DataIncorrect_DoctorDoesNotExistExceptionThrown() {
        Doctor doctor = createDoctor("test@test.pl");
        Clinic clinic = createClinic("Medicover");
        EmploymentDTO employmentDTO = new EmploymentDTO(doctor.getEmail(), clinic.getName());
        when(doctorRepository.findByEmail(any())).thenReturn(Optional.empty());
        DoctorDoesNotExistException exception = assertThrows(DoctorDoesNotExistException.class,
                () -> doctorService.employDoctorInClinic("test@test.pl", employmentDTO));
        //Then
        assertEquals("The doctor you are trying to assign does not exist", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @Test
    void employDoctorInClinic_DataIncorrect_ClinicDoesNotExistExceptionThrown() {
        Doctor doctor = createDoctor("test@test.pl");
        Clinic clinic = createClinic("Medicover");
        EmploymentDTO employmentDTO = new EmploymentDTO(doctor.getEmail(), clinic.getName());
        when(clinicRepository.findByName(any())).thenReturn(Optional.empty());
        when(doctorRepository.findByEmail(any())).thenReturn(Optional.of(doctor));
        ClinicDoesNotExistException exception = assertThrows(ClinicDoesNotExistException.class,
                () -> doctorService.employDoctorInClinic("test@test.pl", employmentDTO));
        //Then
        assertEquals("Clinic with given name does not exist.", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }
}
