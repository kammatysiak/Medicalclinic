package com.kammatysiak.medicalclinic.service;

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
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.kammatysiak.medicalclinic.TestDataFactory.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
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

    @Test
    void deletePatientDoctor_DataCorrect_VoidReturned() {
        Doctor doctor = createDoctor("test@test.pl");

        when(doctorRepository.findByEmail(doctor.getEmail())).thenReturn(Optional.of(doctor));

        doctorService.deleteDoctor(doctor.getEmail());

        verify(doctorRepository).delete(doctor);
    }

    @Test
    void employDoctorInClinic_DataCorrect_VoidReturned() {
        Doctor doctor = createDoctor("test@test.pl");
        Clinic clinic = createClinic("Medicover");
        EmploymentDTO employmentDTO = new EmploymentDTO(doctor.getEmail(),clinic.getName());

        when(doctorRepository.findByEmail(doctor.getEmail())).thenReturn(Optional.of(doctor));
        when(clinicRepository.findByName(clinic.getName())).thenReturn(Optional.of(clinic));
        when(doctorRepository.save(any())).thenReturn(doctor);

        DoctorDTO result = doctorService.employDoctorInClinic(doctor.getEmail(),employmentDTO);

        assertEquals(doctor.getEmail(), result.getEmail());
        assertEquals(doctor.getFirstName(), result.getFirstName());
        assertEquals(doctor.getLastName(), result.getLastName());
        assertEquals(doctor.getSpecialization(), result.getSpecialization());
        assertEquals(doctor.getClinics().stream()
                .map(Clinic::getId)
                .toList(), result.getClinicIds());
    }

}
