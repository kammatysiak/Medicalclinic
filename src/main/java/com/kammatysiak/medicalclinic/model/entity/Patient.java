package com.kammatysiak.medicalclinic.model.entity;

import com.kammatysiak.medicalclinic.model.dto.PatientDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "PATIENTS")
@Entity
@Builder
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "FIRST_NAME")
    private String firstName;
    @Column(name = "LAST_NAME")
    private String lastName;
    @Column(name = "PASSWORD")
    private String password;
    @Column(name = "ID_CARD_NO")
    private String idCardNo;
    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;
    @Column(name = "BIRTHDAY")
    private LocalDate birthday;
    @Column(name = "MODIFY_DATE")
    private LocalDateTime modifyDate;

    public static void setPatientData(Patient patient, PatientDTO newPatientData) {
        patient.setFirstName(newPatientData.getFirstName());
        patient.setBirthday(newPatientData.getBirthday());
        patient.setLastName(newPatientData.getLastName());
        patient.setPhoneNumber(newPatientData.getPhoneNumber());
        patient.setEmail(newPatientData.getEmail());
    }
}
