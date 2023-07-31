package ru.bengo.animaltracking.dto;

import jakarta.validation.constraints.NotBlank;

public record AnimalTypeDto(

        @NotBlank
        String type

) {
}
