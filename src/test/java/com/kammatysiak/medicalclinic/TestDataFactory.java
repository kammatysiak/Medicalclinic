package com.kammatysiak.medicalclinic;

import com.kammatysiak.medicalclinic.model.dto.ClinicDTO;
import com.kammatysiak.medicalclinic.model.dto.DoctorCreateDTO;
import com.kammatysiak.medicalclinic.model.dto.PatientCreateDTO;
import com.kammatysiak.medicalclinic.model.dto.PatientDTO;
import com.kammatysiak.medicalclinic.model.entity.Clinic;
import com.kammatysiak.medicalclinic.model.entity.Doctor;
import com.kammatysiak.medicalclinic.model.entity.Patient;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;

public class TestDataFactory {

    public static Patient createPatient(String email) {
        return Patient.builder()
                .id(1L)
                .email(email)
                .firstName("Tomasz")
                .lastName("Kowalski")
                .birthday(LocalDate.of(2022, 1, 1))
                .idCardNo("123123")
                .password("asdzxc1")
                .phoneNumber("606606606")
                .build();
    }

    public static PatientCreateDTO createPatientCreateDTO(String email) {
        return PatientCreateDTO.builder()
                .email(email)
                .firstName("Tomasz")
                .lastName("Kowalski")
                .birthday(LocalDate.of(2022, 1, 1))
                .idCardNo("123123")
                .password("asdzxc1")
                .phoneNumber("606606606")
                .build();
    }

    public static PatientDTO createPatientDTO(String email) {
        return PatientDTO.builder()
                .email(email)
                .firstName("Tomas")
                .lastName("Kowalsk")
                .birthday(LocalDate.of(2032, 1, 1))
                .phoneNumber("606606607")
                .build();
    }

    public static Doctor createDoctor(String email) {
        return Doctor.builder()
                .id(1L)
                .email(email)
                .firstName("Tomasz")
                .lastName("Kowalski")
                .password("asdzxc1")
                .specialization("Oncology")
                .clinics(new HashSet<>())
                .build();
    }

    public static DoctorCreateDTO createDoctorCreateDTO(String email) {
        return DoctorCreateDTO.builder()
                .email(email)
                .firstName("Tomasz")
                .lastName("Kowalski")
                .password("asdzxc1")
                .specialization("Oncology")
                .build();
    }

    public static Clinic createClinic(String name) {
        return Clinic.builder()
                .buildingNumber("12")
                .city("Warsaw")
                .doctors(new HashSet<>())
                .id(1L)
                .name(name)
                .postCode("01-001")
                .street("Marszałkowska")
                .build();
    }

    public static ClinicDTO createClinicDTO(String name) {
        return ClinicDTO.builder()
                .buildingNumber("12")
                .city("Warsaw")
                .name(name)
                .postCode("01-001")
                .street("Marszałkowska")
                .doctorIds(new ArrayList<>())
                .build();
    }
}
