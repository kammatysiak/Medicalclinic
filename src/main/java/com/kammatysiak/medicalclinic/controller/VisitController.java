package com.kammatysiak.medicalclinic.controller;

import com.kammatysiak.medicalclinic.model.dto.VisitAssignPatientDTO;
import com.kammatysiak.medicalclinic.model.dto.VisitDTO;
import com.kammatysiak.medicalclinic.service.VisitService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/visits")
public class VisitController {

    private final VisitService visitService;

    @GetMapping("/{email}")
    public List<VisitDTO> getVisitsForPatient(@PathVariable("email") String email) {
        return visitService.getVisitsForPatient(email);
    }
    @GetMapping("/{id}")
    public List<VisitDTO> getVisitsForPatient(@PathVariable("id") long id) {
        return visitService.getVisitsForPatient(id);
    }
    @PostMapping
    public VisitDTO postVisit(@RequestBody VisitDTO visitDTO) {
        return visitService.createVisit(visitDTO);
    }

    @PatchMapping("/{email}")
    public VisitDTO assignPatientToVisit(@PathVariable("email") String email, @RequestBody VisitAssignPatientDTO assignDTO) {
        return visitService.assignPatientToVisit(email, assignDTO);
    }
}
