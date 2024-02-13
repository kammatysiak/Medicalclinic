package com.kammatysiak.medicalclinic.controller;

import com.kammatysiak.medicalclinic.model.dto.DoctorDTO;
import com.kammatysiak.medicalclinic.model.dto.EmploymentDTO;
import com.kammatysiak.medicalclinic.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/doctors")
public class DoctorController {

    private final DoctorService doctorService;

    @GetMapping
    public List<DoctorDTO> getDoctors() {
        return doctorService.getDoctors();
    }

    @GetMapping("/{email}")
    public DoctorDTO getDoctor(@PathVariable("email") String email) {
        return doctorService.getDoctor(email);
    }

    @PostMapping
    public DoctorDTO postDoctor(@RequestBody DoctorDTO doctorDTO) {
        return doctorService.createDoctor(doctorDTO);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{email}")
    public void deleteDoctor(@PathVariable("email") String email) {
        doctorService.deleteDoctor(email);
    }

    @PutMapping("/{email}")
    public DoctorDTO employDoctorInClinic(@PathVariable("email") String email, @RequestBody EmploymentDTO employmentDTO) {
        return doctorService.employDoctorInClinic(email, employmentDTO);
    }
}
