package ru.bengo.animaltracking.api.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VisitedLocationUpdateDto {

    @NotNull
    @Positive
    Long visitedLocationPointId;

    @NotNull
    @Positive
    Long locationPointId;
}
