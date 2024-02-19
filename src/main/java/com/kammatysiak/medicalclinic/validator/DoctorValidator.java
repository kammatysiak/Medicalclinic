package com.kammatysiak.medicalclinic.validator;

import com.kammatysiak.medicalclinic.exceptions.DoctorExistsException;
import com.kammatysiak.medicalclinic.exceptions.DoctorNullFieldException;
import com.kammatysiak.medicalclinic.model.entity.Doctor;
import org.springframework.http.HttpStatus;

import java.util.Objects;
import java.util.stream.Stream;

public class DoctorValidator {

    public static void validateNullsDoctor(Doctor doctor, String message) {
        if (Stream.of(doctor.getEmail(), doctor.getFirstName(), doctor.getLastName(), doctor.getSpecialization(), doctor.getPassword())
                .anyMatch(Objects::isNull)) {
            throw new DoctorNullFieldException(message, HttpStatus.BAD_REQUEST);
        }
    }

    public static void validateIfDoctorAlreadyExists(boolean exists, String message) {
        if (exists) {
            throw new DoctorExistsException(message, HttpStatus.CONFLICT);
        }
    }
}
