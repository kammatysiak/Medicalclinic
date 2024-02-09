package com.kammatysiak.medicalclinic.validator;

import com.kammatysiak.medicalclinic.exceptions.PatientDoesNotExistException;
import com.kammatysiak.medicalclinic.exceptions.PatientExistsException;
import com.kammatysiak.medicalclinic.exceptions.PatientNullFieldException;
import com.kammatysiak.medicalclinic.exceptions.PatientPasswordValidationException;
import com.kammatysiak.medicalclinic.model.dto.PasswordClassDTO;
import com.kammatysiak.medicalclinic.model.dto.PatientDTO;
import com.kammatysiak.medicalclinic.model.entity.Patient;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.Arrays;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PatientValidator {


    public static void validateNullsPatient(Patient patient, String message) {
        if (Arrays.asList(patient.getBirthday(), patient.getEmail(), patient.getFirstName(), patient.getLastName(),
                patient.getIdCardNo(), patient.getPassword(), patient.getPhoneNumber()).contains(null)) {
            throw new PatientNullFieldException(message, HttpStatus.BAD_REQUEST);
        }
    }

    public static void validatePasswordChange(PasswordClassDTO passwords, Patient patient, String message) {
        if (!patient.getPassword().equals(passwords.getOldPassword())) {
            throw new PatientPasswordValidationException(message, HttpStatus.PRECONDITION_FAILED);
        }
    }

    public static void validateNullsPatientDTO(PatientDTO patient, String message) {
        if (Arrays.asList(patient.getBirthday(), patient.getEmail(), patient.getFirstName(),
                patient.getLastName(), patient.getPhoneNumber()).contains(null)) {
            throw new PatientNullFieldException(message, HttpStatus.BAD_REQUEST);
        }
    }

    public static void validateIfPatientIsNull(boolean exists, String message) {
        if (!exists) {
            throw new PatientDoesNotExistException(message, HttpStatus.NOT_FOUND);
        }
    }

    public static void validateIfPatientAlreadyExists(boolean exists, String message) {
        if (exists) {
            throw new PatientExistsException(message, HttpStatus.CONFLICT);
        }
    }

}

