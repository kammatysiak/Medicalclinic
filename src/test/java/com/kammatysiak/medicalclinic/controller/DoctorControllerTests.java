package com.kammatysiak.medicalclinic.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kammatysiak.medicalclinic.model.dto.DoctorCreateDTO;
import com.kammatysiak.medicalclinic.model.dto.DoctorDTO;
import com.kammatysiak.medicalclinic.model.dto.EmploymentDTO;
import com.kammatysiak.medicalclinic.service.DoctorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static com.kammatysiak.medicalclinic.TestDataFactory.createDoctorCreateDTO;
import static com.kammatysiak.medicalclinic.TestDataFactory.createDoctorDTO;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class DoctorControllerTests {
    @MockBean
    DoctorService doctorService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void getDoctors_DataCorrect_ListDoctorDTOReturned() throws Exception {
        DoctorDTO doctor = createDoctorDTO("test@test.pl");
        DoctorDTO doctor1 = createDoctorDTO("test1@test.pl");


        when(doctorService.getDoctors()).thenReturn(List.of(doctor, doctor1));

        mockMvc.perform(MockMvcRequestBuilders.get("/doctors"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].email").value(doctor.getEmail()))
                .andExpect(jsonPath("$[1].email").value(doctor1.getEmail()));
    }

    @Test
    void getDoctor_DataCorrect_DoctorDTOReturned() throws Exception {
        DoctorDTO doctorDTO = createDoctorDTO("test@test.pl");
        when(doctorService.getDoctor(any())).thenReturn(doctorDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/doctors/test@test.pl"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(doctorDTO.getEmail()))
                .andExpect(jsonPath("$.firstName").value(doctorDTO.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(doctorDTO.getLastName()))
                .andExpect(jsonPath("$.specialization").value(doctorDTO.getSpecialization()))
                .andExpect(jsonPath("$.clinicIds").value(doctorDTO.getClinicIds()));
    }

    @Test
    void createDoctor_DataCorrect_DoctorDTOReturned() throws Exception {
        DoctorCreateDTO doctorCreateDTO = createDoctorCreateDTO("test@test.pl");
        DoctorDTO doctorDTO = createDoctorDTO("test@test.pl");
        when(doctorService.createDoctor(any())).thenReturn(doctorDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/doctors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(doctorCreateDTO)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(doctorDTO.getEmail()))
                .andExpect(jsonPath("$.firstName").value(doctorDTO.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(doctorDTO.getLastName()))
                .andExpect(jsonPath("$.specialization").value(doctorDTO.getSpecialization()))
                .andExpect(jsonPath("$.clinicIds").value(doctorDTO.getClinicIds()));
    }

    @Test
    void deleteDoctor_DataCorrect_DoctorDeleted() throws Exception {
        doNothing().when(doctorService).deleteDoctor(any());

        mockMvc.perform(MockMvcRequestBuilders.delete("/doctors/test@test.pl"))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    void assignDoctorToClinic_DataCorrect_DoctorDeleted() throws Exception {
        DoctorDTO doctorDTO = createDoctorDTO("test@test.pl");
        EmploymentDTO employmentDTO = new EmploymentDTO("test@test.pl", "Medicover");
        when(doctorService.employDoctorInClinic(any(), any())).thenReturn(doctorDTO);

        mockMvc.perform(MockMvcRequestBuilders.put("/doctors/test@test.pl")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employmentDTO)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(doctorDTO.getEmail()))
                .andExpect(jsonPath("$.firstName").value(doctorDTO.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(doctorDTO.getLastName()))
                .andExpect(jsonPath("$.specialization").value(doctorDTO.getSpecialization()))
                .andExpect(jsonPath("$.clinicIds").value(doctorDTO.getClinicIds()));
    }
}
