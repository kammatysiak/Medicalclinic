package com.kammatysiak.medicalclinic.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "PATIENTS")
@Entity
public class Patient {
    @Id
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

}
