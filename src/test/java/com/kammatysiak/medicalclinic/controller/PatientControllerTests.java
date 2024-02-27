package com.kammatysiak.medicalclinic.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kammatysiak.medicalclinic.model.dto.PasswordDTO;
import com.kammatysiak.medicalclinic.model.dto.PatientCreateDTO;
import com.kammatysiak.medicalclinic.model.dto.PatientDTO;
import com.kammatysiak.medicalclinic.service.PatientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static com.kammatysiak.medicalclinic.TestDataFactory.createPatientCreateDTO;
import static com.kammatysiak.medicalclinic.TestDataFactory.createPatientDTO;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PatientControllerTests {
    @MockBean
    PatientService patientService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void getPatients_DataCorrect_ListPatientDTOReturned() throws Exception {
        PatientDTO patient = createPatientDTO("test@test.pl");
        PatientDTO patient1 = createPatientDTO("test1@test.pl");

        when(patientService.getPatients()).thenReturn(List.of(patient, patient1));

        mockMvc.perform(MockMvcRequestBuilders.get("/patients"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].email").value(patient.getEmail()))
                .andExpect(jsonPath("$[1].email").value(patient1.getEmail()));
    }

    @Test
    void getPatient_DataCorrect_PatientDTOReturned() throws Exception {
        PatientDTO patientDTO = createPatientDTO("test@Test.pl");
        when(patientService.getPatient(any())).thenReturn(patientDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/patients/test@test.pl"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(patientDTO.getEmail()))
                .andExpect(jsonPath("$.firstName").value(patientDTO.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(patientDTO.getLastName()))
                .andExpect(jsonPath("$.birthday").value(patientDTO.getBirthday().toString()))
                .andExpect(jsonPath("$.phoneNumber").value(patientDTO.getPhoneNumber()));
    }

    @Test
    void postPatient_DataCorrect_PatientDTOReturned() throws Exception {
        PatientCreateDTO patientCreateDTO = createPatientCreateDTO("test@test.pl");
        PatientDTO patientDTO = createPatientDTO("test@test.pl");
        when(patientService.createPatient(any())).thenReturn(patientDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/patients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patientCreateDTO)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(patientDTO.getEmail()))
                .andExpect(jsonPath("$.firstName").value(patientDTO.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(patientDTO.getLastName()))
                .andExpect(jsonPath("$.birthday").value(patientDTO.getBirthday().toString()))
                .andExpect(jsonPath("$.phoneNumber").value(patientDTO.getPhoneNumber()));
    }

    @Test
    void deletePatient_DataCorrect_PatientDeleted() throws Exception {
        PatientDTO patientDTO = createPatientDTO("test@test.pl");
        doNothing().when(patientService).deletePatient(any());

        mockMvc.perform(MockMvcRequestBuilders.delete("/patients/test@test.pl")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    void editPatient_DataCorrect_PatientDTOReturned() throws Exception {
        PatientDTO patientDTO = createPatientDTO("test@Test.pl");
        when(patientService.editPatient(any(), any())).thenReturn(patientDTO);

        mockMvc.perform(MockMvcRequestBuilders.put("/patients/test@test.pl")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patientDTO)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(patientDTO.getEmail()))
                .andExpect(jsonPath("$.firstName").value(patientDTO.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(patientDTO.getLastName()))
                .andExpect(jsonPath("$.birthday").value(patientDTO.getBirthday().toString()))
                .andExpect(jsonPath("$.phoneNumber").value(patientDTO.getPhoneNumber()));

    }

    @Test
    void editPatientPassword_DataCorrect_PatientDTOReturned() throws Exception {
        PatientDTO patientDTO = createPatientDTO("test@test.pl");
        PasswordDTO passwordDTO = new PasswordDTO("123", "321");
        when(patientService.editPatientPassword(any(), any())).thenReturn(patientDTO);

        mockMvc.perform(MockMvcRequestBuilders.patch("/patients/test@test.pl")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(passwordDTO)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(patientDTO.getEmail()))
                .andExpect(jsonPath("$.firstName").value(patientDTO.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(patientDTO.getLastName()))
                .andExpect(jsonPath("$.birthday").value(patientDTO.getBirthday().toString()))
                .andExpect(jsonPath("$.phoneNumber").value(patientDTO.getPhoneNumber()));

    }
}
