package ru.bengo.animaltracking.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class VisitedLocationUpdateDto {
    @NotNull
    @Positive
    private Long visitedLocationPointId;

    @NotNull
    @Positive
    private Long locationPointId;
}
