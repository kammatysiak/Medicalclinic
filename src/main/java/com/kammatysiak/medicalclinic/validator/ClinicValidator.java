package com.kammatysiak.medicalclinic.validator;

import com.kammatysiak.medicalclinic.exceptions.ClinicExistsException;
import com.kammatysiak.medicalclinic.exceptions.ClinicNullFieldException;
import com.kammatysiak.medicalclinic.model.entity.Clinic;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.Objects;
import java.util.stream.Stream;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ClinicValidator {

    public static void validateNullsClinic(Clinic clinic, String message) {
        if (Stream.of(clinic.getCity(), clinic.getBuildingNumber(), clinic.getName(), clinic.getStreet(), clinic.getPostCode()).anyMatch(Objects::isNull)) {
            throw new ClinicNullFieldException(message, HttpStatus.BAD_REQUEST);
        }
    }

    public static void validateIfClinicAlreadyExists(boolean exists, String message) {
        if (exists) {
            throw new ClinicExistsException(message, HttpStatus.CONFLICT);
        }
    }
}
