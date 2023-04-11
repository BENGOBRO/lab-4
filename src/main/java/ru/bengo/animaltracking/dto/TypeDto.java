package ru.bengo.animaltracking.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

public record TypeDto (
        @NotNull
        @Positive
        Long oldTypeId,

        @NotNull
        @Positive
        Long newTypeId
) {}
