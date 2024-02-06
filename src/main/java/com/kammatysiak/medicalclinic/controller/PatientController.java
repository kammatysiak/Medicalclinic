package com.kammatysiak.medicalclinic.controller;

import com.kammatysiak.medicalclinic.model.dto.PasswordClassDTO;
import com.kammatysiak.medicalclinic.model.dto.PatientCreateDTO;
import com.kammatysiak.medicalclinic.model.dto.PatientDTO;
import com.kammatysiak.medicalclinic.model.entity.Patient;
import com.kammatysiak.medicalclinic.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/patients")
public class PatientController {

    private final PatientService patientService;
//w controllerze definiujemy endpointy (punkty komunikacji) a serwis ma dostarczyc wlasciwe dane do kontrolera

    @GetMapping
    public List<PatientDTO> getPatients() {
        return patientService.getPatients();
    }

    @GetMapping("/{email}")
    public PatientDTO getPatient(@PathVariable String email) {
        return patientService.getPatient(email);
    }

    @PostMapping
    public PatientDTO postPatient(@RequestBody PatientCreateDTO patientDTO) {
       return patientService.createPatient(patientDTO);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{email}")
    public void deletePatient(@PathVariable String email) {
        patientService.deletePatient(email);
    }

    @PutMapping("/{email}")
    public PatientDTO editPatient(@PathVariable String email, @RequestBody PatientDTO patientDTO) {
       return patientService.editPatient(email, patientDTO);
    }
    @PatchMapping("/{email}")
    public PatientDTO editPatientPassword(@PathVariable String email, @RequestBody PasswordClassDTO passwordsDTO) {
        return patientService.editPatientPassword(email, passwordsDTO);
    }


}
