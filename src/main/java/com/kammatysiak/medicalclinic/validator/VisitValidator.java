package com.kammatysiak.medicalclinic.validator;

import com.kammatysiak.medicalclinic.exceptions.VisitNullFieldException;
import com.kammatysiak.medicalclinic.exceptions.VisitTimingException;
import com.kammatysiak.medicalclinic.model.dto.VisitDTO;
import com.kammatysiak.medicalclinic.model.entity.Visit;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.Objects;
import java.util.stream.Stream;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class VisitValidator {

    public static void validateNullsVisit(Visit visit, String message) {
        if (Stream.of(visit.getClinic(), visit.getVisitEnd(), visit.getVisitStart(), visit.getDoctor())
                .anyMatch(Objects::isNull)) {
            throw new VisitNullFieldException(message, HttpStatus.BAD_REQUEST);
        }
    }
    public static void validateNullsVisitDTO(VisitDTO visitDTO, String message) {
        if (Stream.of(visitDTO.getClinicId(), visitDTO.getVisitStart(), visitDTO.getVisitEnd(), visitDTO.getDoctorId())
                .anyMatch(Objects::isNull)) {
            throw new VisitNullFieldException(message, HttpStatus.BAD_REQUEST);
        }
    }

    public static void validateTimingVisit(Visit visit, String message) {
        if (visit.getVisitStart().getMinute() % 15 != 0 || visit.getVisitEnd().getMinute() % 15 != 0) {
            throw new VisitTimingException(message, HttpStatus.BAD_REQUEST);
        }
    }

}
