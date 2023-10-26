package ru.bengo.animaltracking.api.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VisitedLocationDto {

        Long id;

        Date dateTimeOfVisitLocationPoint;

        @NotNull
        @Positive
        Long locationPointId;
}
