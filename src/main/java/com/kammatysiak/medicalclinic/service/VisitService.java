package com.kammatysiak.medicalclinic.service;

import com.kammatysiak.medicalclinic.exceptions.*;
import com.kammatysiak.medicalclinic.mapper.VisitMapper;
import com.kammatysiak.medicalclinic.model.dto.VisitAssignPatientDTO;
import com.kammatysiak.medicalclinic.model.dto.VisitDTO;
import com.kammatysiak.medicalclinic.model.entity.Clinic;
import com.kammatysiak.medicalclinic.model.entity.Doctor;
import com.kammatysiak.medicalclinic.model.entity.Patient;
import com.kammatysiak.medicalclinic.model.entity.Visit;
import com.kammatysiak.medicalclinic.repository.ClinicRepository;
import com.kammatysiak.medicalclinic.repository.DoctorRepository;
import com.kammatysiak.medicalclinic.repository.PatientRepository;
import com.kammatysiak.medicalclinic.repository.VisitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.kammatysiak.medicalclinic.model.entity.Visit.setVisitData;
import static com.kammatysiak.medicalclinic.validator.VisitValidator.validateNullsVisit;
import static com.kammatysiak.medicalclinic.validator.VisitValidator.validateTimingVisit;

@RequiredArgsConstructor
@Service
public class VisitService {

    private final VisitRepository visitRepository;
    private final VisitMapper visitMapper;
    private final PatientRepository patientRepository;
    private final ClinicRepository clinicRepository;
    private final DoctorRepository doctorRepository;

    public List<VisitDTO> getVisitsForPatient(String email) {
        return visitRepository.findAll().stream()
                .filter(v -> v.getPatient().getEmail().equals(email))
                .map(visitMapper::toVisitDTO)
                .toList();
    }

    public VisitDTO createVisit(VisitDTO visitDTO) {
        validateNullsVisit(visitMapper.toVisit(visitDTO), "The Visit data shared is incomplete.");
        validateTimingVisit(visitMapper.toVisit(visitDTO), "You are trying to book the visit with an incorrect time");
        if (!(visitRepository.findAllOverlapping(visitDTO.getDoctorId(), visitDTO.getVisitStart(), visitDTO.getVisitEnd()).isEmpty())) {
            throw new VisitTimingException("The visit you are trying to book is overlapping an existing visit", HttpStatus.BAD_REQUEST);
        }
        Visit visit = new Visit();
        Clinic clinic = clinicRepository.findById(visitDTO.getClinicId())
                .orElseThrow(() -> new ClinicDoesNotExistException("Clinic does not exist.", HttpStatus.NOT_FOUND));
        Doctor doctor = doctorRepository.findById(visitDTO.getDoctorId())
                .orElseThrow(() -> new DoctorDoesNotExistException("Doctor does not Exist.", HttpStatus.NOT_FOUND));
        setVisitData(visit, clinic, doctor, visitDTO);
        return visitMapper.toVisitDTO(visit);
    }

    public VisitDTO assignPatientToVisit(String email, VisitAssignPatientDTO assignDTO) {
        Visit visit = visitRepository.findById(assignDTO.getVisitID())
                .orElseThrow(() -> new VisitDoesNotExistException("The visit you are trying to change does not exist.", HttpStatus.BAD_REQUEST));
        Patient patient = patientRepository.findByEmail(assignDTO.getEmail())
                .orElseThrow(() -> new PatientDoesNotExistException("The patient you are trying to change does not Exist", HttpStatus.BAD_REQUEST));
        visit.setPatient(patient);
        visitRepository.save(visit);
        return visitMapper.toVisitDTO(visit);
    }

}
