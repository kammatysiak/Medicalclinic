package com.kammatysiak.medicalclinic.validator;

import com.kammatysiak.medicalclinic.exceptions.PatientPasswordValidationException;
import com.kammatysiak.medicalclinic.model.dto.PasswordClassDTO;
import com.kammatysiak.medicalclinic.model.dto.PatientDTO;
import com.kammatysiak.medicalclinic.model.entity.Patient;

import java.util.Arrays;
import java.util.List;

public class PatientValidator {

    public static boolean validateNullsPatient(Patient patient) {
      return Arrays.asList(patient.getBirthday(), patient.getEmail(), patient.getFirstName(), patient.getLastName(),
                patient.getIdCardNo(), patient.getPassword(), patient.getPhoneNumber()).contains(null);
    }

public static void validatePasswordChange(PasswordClassDTO passwords, Patient patient){
        if (!patient.getPassword().equals(passwords.getOldPassword())) {
            throw new PatientPasswordValidationException();
        }
}
    public static boolean validateNullsPatientDTO(PatientDTO patient) {
        return Arrays.asList(patient.getBirthday(), patient.getEmail(), patient.getFirstName(), patient.getLastName(),patient.getPhoneNumber()).contains(null);
    }
}
