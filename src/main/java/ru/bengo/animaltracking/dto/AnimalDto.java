package ru.bengo.animaltracking.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;
import ru.bengo.animaltracking.annotation.GenderAnnotation;
import ru.bengo.animaltracking.annotation.LifeStatusAnnotation;

import java.util.List;


public record AnimalDto (
        List<Long> animalTypes,

        @NotNull
        @Positive
        Double weight,

        @NotNull
        @Positive
        Double length,

        @NotNull
        @Positive
        Double height,

        @NotNull
        @GenderAnnotation
        String gender,

        @NotNull
        @LifeStatusAnnotation
        String lifeStatus,

        @NotNull
        @Positive
        Integer chipperId,

        @NotNull
        @Positive
        Long chippingLocationId
) {}
