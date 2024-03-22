package com.kammatysiak.medicalclinic.controller;

import com.kammatysiak.medicalclinic.model.dto.PasswordDTO;
import com.kammatysiak.medicalclinic.model.dto.PatientCreateDTO;
import com.kammatysiak.medicalclinic.model.dto.PatientDTO;
import com.kammatysiak.medicalclinic.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/patients")
public class PatientController {

    private final PatientService patientService;


    @GetMapping
    public List<PatientDTO> getPatients() {
        return patientService.getPatients();
    }

    @GetMapping("/{email}")
    public PatientDTO getPatient(@PathVariable("email") String email) {
        return patientService.getPatient(email);
    }
    @GetMapping("/id/{id}")
    public PatientDTO getPatientById(@PathVariable("id") long id) {
        return patientService.getPatient(id);
    }

    @PostMapping
    public PatientDTO postPatient(@RequestBody PatientCreateDTO patientDTO) {
        return patientService.createPatient(patientDTO);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{email}")
    public void deletePatient(@PathVariable("email") String email) {
        patientService.deletePatient(email);
    }

    @PutMapping("/{email}")
    public PatientDTO editPatient(@PathVariable("email") String email, @RequestBody PatientDTO patientDTO) {
        return patientService.editPatient(email, patientDTO);
    }

    @PatchMapping("/{email}")
    public PatientDTO editPatientPassword(@PathVariable("email") String email, @RequestBody PasswordDTO passwordsDTO) {
        return patientService.editPatientPassword(email, passwordsDTO);
    }
    @GetMapping("/date/{date}")
    public List<PatientDTO> getPatientsWithVisitAtDate(@RequestParam LocalDate date){
        return patientService.getPatientsWithVisitAtDate(date);
    }
}
