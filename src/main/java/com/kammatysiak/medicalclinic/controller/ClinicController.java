package com.kammatysiak.medicalclinic.controller;

import com.kammatysiak.medicalclinic.model.dto.ClinicDTO;
import com.kammatysiak.medicalclinic.service.ClinicService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/clinics")
public class ClinicController {

    private final ClinicService clinicService;

    @GetMapping
    public List<ClinicDTO> getClinics() {
        return clinicService.getClinics();
    }

    @GetMapping("/{name}")
    public ClinicDTO getClinic(@PathVariable("name") String name) {
        return clinicService.getClinic(name);
    }

    @PostMapping
    public ClinicDTO createClinic(@RequestBody ClinicDTO clinicDTO) {
        return clinicService.createClinic(clinicDTO);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{name}")
    public void deleteClinic(@PathVariable("name") String name) {
        clinicService.deleteClinic(name);
    }
}
