package ru.bengo.animaltracking.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record AnimalVisitedLocationDto(

        @NotNull
        @Positive
        Long visitedLocationPointId,

        @NotNull
        @Positive
        Long locationPointId
) {
}
