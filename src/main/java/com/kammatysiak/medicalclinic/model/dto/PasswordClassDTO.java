package com.kammatysiak.medicalclinic.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PasswordClassDTO {
    private String oldPassword;
    private String newPassword;
}
