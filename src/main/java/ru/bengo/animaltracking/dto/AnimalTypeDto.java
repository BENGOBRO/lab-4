package ru.bengo.animaltracking.dto;

import jakarta.validation.constraints.NotBlank;

public record AnimalTypeDto(
        Long id,
        @NotBlank
        String type

) {
}
