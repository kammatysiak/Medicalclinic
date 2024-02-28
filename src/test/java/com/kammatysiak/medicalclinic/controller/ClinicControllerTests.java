package com.kammatysiak.medicalclinic.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kammatysiak.medicalclinic.model.dto.ClinicDTO;
import com.kammatysiak.medicalclinic.service.ClinicService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static com.kammatysiak.medicalclinic.TestDataFactory.createClinicDTO;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ClinicControllerTests {
    @MockBean
    ClinicService clinicService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void getClinics_DataCorrect_ListClinicDTOReturned() throws Exception {
        ClinicDTO clinicDTO = createClinicDTO("Medicover");
        ClinicDTO clinicDTO1 = createClinicDTO("Medicover1");

        when(clinicService.getClinics()).thenReturn(List.of(clinicDTO, clinicDTO1));

        mockMvc.perform(MockMvcRequestBuilders.get("/clinics"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(clinicDTO.getName()))
                .andExpect(jsonPath("$[1].name").value(clinicDTO1.getName()));
    }

    @Test
    void getClinic_DataCorrect_ClinicDTOReturned() throws Exception {
        ClinicDTO clinicDTO = createClinicDTO("Medicover");
        when(clinicService.getClinic(anyLong())).thenReturn(clinicDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/clinics/{id}", 1))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(clinicDTO.getName()))
                .andExpect(jsonPath("$.buildingNumber").value(clinicDTO.getBuildingNumber()))
                .andExpect(jsonPath("$.city").value(clinicDTO.getCity()))
                .andExpect(jsonPath("$.postCode").value(clinicDTO.getPostCode()))
                .andExpect(jsonPath("$.street").value(clinicDTO.getStreet()));

    }

    @Test
    void createClinic_DataCorrect_ClinicDTOReturned() throws Exception {
        ClinicDTO clinicDTO = createClinicDTO("Medicover");
        when(clinicService.createClinic(any())).thenReturn(clinicDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/clinics")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clinicDTO)))
                .andDo(print())
                .andExpect(jsonPath("$.name").value(clinicDTO.getName()))
                .andExpect(jsonPath("$.buildingNumber").value(clinicDTO.getBuildingNumber()))
                .andExpect(jsonPath("$.city").value(clinicDTO.getCity()))
                .andExpect(jsonPath("$.postCode").value(clinicDTO.getPostCode()))
                .andExpect(jsonPath("$.street").value(clinicDTO.getStreet()))
                .andExpect(jsonPath("$.doctorIds").value(clinicDTO.getDoctorIds()));

    }

    @Test
    void deleteClinic_DataCorrect_ClinicDeleted() throws Exception {
        doNothing().when(clinicService).deleteClinic(any());

        mockMvc.perform(MockMvcRequestBuilders.delete("/clinics/test@test.pl"))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

}
