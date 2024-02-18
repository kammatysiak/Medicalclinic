package com.kammatysiak.medicalclinic.service;

import com.kammatysiak.medicalclinic.exceptions.ClinicDoesNotExistException;
import com.kammatysiak.medicalclinic.exceptions.DoctorDoesNotExistException;
import com.kammatysiak.medicalclinic.mapper.DoctorMapper;
import com.kammatysiak.medicalclinic.model.dto.DoctorCreateDTO;
import com.kammatysiak.medicalclinic.model.dto.DoctorDTO;
import com.kammatysiak.medicalclinic.model.dto.EmploymentDTO;
import com.kammatysiak.medicalclinic.model.entity.Clinic;
import com.kammatysiak.medicalclinic.model.entity.Doctor;
import com.kammatysiak.medicalclinic.repository.ClinicRepository;
import com.kammatysiak.medicalclinic.repository.DoctorRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.kammatysiak.medicalclinic.validator.DoctorValidator.validateIfDoctorAlreadyExists;
import static com.kammatysiak.medicalclinic.validator.DoctorValidator.validateNullsDoctor;

@RequiredArgsConstructor
@Service
public class DoctorService {

    private final DoctorRepository doctorRepository;
    private final ClinicRepository clinicRepository;
    private final DoctorMapper doctorMapper;

    public List<DoctorDTO> getDoctors() {
        return doctorRepository.findAll().stream()
                .map(doctorMapper::ToDoctorDTO)
                .toList();
    }

    public DoctorDTO getDoctor(String email) {
        Doctor doctor = doctorRepository.findByEmail(email)
                .orElseThrow(() -> new DoctorDoesNotExistException("Doctor with given email does not exist.", HttpStatus.NOT_FOUND));
        return doctorMapper.ToDoctorDTO(doctor);
    }
    @Transactional
    public DoctorDTO createDoctor(DoctorCreateDTO doctorCreateDTO) {
        validateNullsDoctor(doctorMapper.ToDoctor(doctorCreateDTO), "Data you shared contains empty fields");
        validateIfDoctorAlreadyExists(doctorRepository.existsByEmail(doctorCreateDTO.getEmail()), "Doctor with given email already exists.");
        Doctor doctor = doctorMapper.ToDoctor(doctorCreateDTO);
        return doctorMapper.ToDoctorDTO(doctorRepository.save(doctor));
    }

    public void deleteDoctor(String email) {
        Doctor doctor = doctorRepository.findByEmail(email)
                .orElseThrow(() -> new DoctorDoesNotExistException("The doctor you are trying to delete does not exist", HttpStatus.NOT_FOUND));
        doctorRepository.delete(doctor);
    }
    @Transactional
    public DoctorDTO employDoctorInClinic(String email, EmploymentDTO employmentDTO) {
        Doctor doctor = doctorRepository.findByEmail(email)
                .orElseThrow(() -> new DoctorDoesNotExistException("The doctor you are trying to assign does not exist", HttpStatus.NOT_FOUND));
        Clinic clinic = clinicRepository.findByName(employmentDTO.getName())
                .orElseThrow(() -> new ClinicDoesNotExistException("Clinic with given name does not exist.", HttpStatus.NOT_FOUND));
        doctor.getClinics().add(clinic);
        doctorRepository.save(doctor);
        return doctorMapper.ToDoctorDTO(doctor);
    }
}
