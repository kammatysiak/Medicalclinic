package com.kammatysiak.medicalclinic.service;

import com.kammatysiak.medicalclinic.mapper.ClinicMapper;
import com.kammatysiak.medicalclinic.model.dto.ClinicDTO;
import com.kammatysiak.medicalclinic.model.entity.Clinic;
import com.kammatysiak.medicalclinic.repository.ClinicRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.kammatysiak.medicalclinic.TestDataFactory.createClinic;
import static com.kammatysiak.medicalclinic.TestDataFactory.createClinicDTO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ClinicServiceTests {

    ClinicService clinicService;
    ClinicRepository clinicRepository;
    ClinicMapper clinicMapper;

    @BeforeEach
    void setup() {
        this.clinicRepository = Mockito.mock(ClinicRepository.class);
        this.clinicMapper = Mappers.getMapper(ClinicMapper.class);
        this.clinicService = new ClinicService(clinicRepository, clinicMapper);
    }

    @Test
    void getClinics_DataCorrect_ListClinicDTOReturned() {
        Clinic clinic = createClinic("Medicover");
        Clinic clinic1 = createClinic("Medicover2");

        List<Clinic> clinics = new ArrayList<>();
        clinics.add(clinic);
        clinics.add(clinic1);


        when(clinicRepository.findAll()).thenReturn(clinics);

        List<ClinicDTO> result = clinicService.getClinics();

        assertEquals(clinic.getBuildingNumber(), result.get(0).getBuildingNumber());
        assertEquals(clinic.getCity(), result.get(0).getCity());
        assertEquals(clinic.getName(), result.get(0).getName());
        assertEquals(clinic.getPostCode(), result.get(0).getPostCode());
        assertEquals(clinic.getStreet(), result.get(0).getStreet());
        assertEquals(clinic1.getBuildingNumber(), result.get(1).getBuildingNumber());
        assertEquals(clinic1.getCity(), result.get(1).getCity());
        assertEquals(clinic1.getName(), result.get(1).getName());
        assertEquals(clinic1.getPostCode(), result.get(1).getPostCode());
        assertEquals(clinic1.getStreet(), result.get(1).getStreet());

    }

    @Test
    void getClinic_DataCorrect_ClinicDTOReturned() {
        Clinic clinic = createClinic("Medicover");

        when(clinicRepository.findById(clinic.getId())).thenReturn(Optional.of(clinic));

        ClinicDTO result = clinicService.getClinic(clinic.getId());
        assertEquals(clinic.getBuildingNumber(), result.getBuildingNumber());
        assertEquals(clinic.getCity(), result.getCity());
        assertEquals(clinic.getName(), result.getName());
        assertEquals(clinic.getPostCode(), result.getPostCode());
        assertEquals(clinic.getStreet(), result.getStreet());

    }

    @Test
    void createClinic_DataCorrect_ClinicDTOReturned() {
        Clinic clinic = createClinic("Medicover");
        ClinicDTO clinicDTO = createClinicDTO("Medicover");

        when(clinicRepository.existsByName(clinicDTO.getName())).thenReturn(false);
        when(clinicRepository.save(any())).thenReturn(clinic);

        ClinicDTO result = clinicService.createClinic(clinicDTO);
        assertEquals(clinic.getBuildingNumber(), result.getBuildingNumber());
        assertEquals(clinic.getCity(), result.getCity());
        assertEquals(clinic.getName(), result.getName());
        assertEquals(clinic.getPostCode(), result.getPostCode());
        assertEquals(clinic.getStreet(), result.getStreet());
    }
    @Test
    void deleteClinic_DataCorrect_VoidReturned(){
        Clinic clinic = createClinic("Medicover");

        when(clinicRepository.findByName(clinic.getName())).thenReturn(Optional.of(clinic));

        clinicService.deleteClinic(clinic.getName());
        verify(clinicRepository).delete(clinic);
    }

}
