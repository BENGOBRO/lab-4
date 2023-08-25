package ru.bengo.animaltracking.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import ru.bengo.animaltracking.annotation.GenderAnnotation;
import ru.bengo.animaltracking.annotation.LifeStatusAnnotation;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;


public record AnimalDto (
        @JsonProperty("animalTypes")
//        @NotNull
//        @Min(1)
        List<Long> animalTypes,

        @NotNull
        @Positive
        @JsonProperty("weight")
        Double weight,

        @NotNull
        @Positive
        @JsonProperty("length")
        Double length,

        @NotNull
        @Positive
        @JsonProperty("height")
        Double height,

        @NotNull
        @GenderAnnotation
        @JsonProperty("gender")
        String gender,

        @LifeStatusAnnotation
        @JsonProperty("lifeStatus")
        String lifeStatus,

        @JsonProperty("chippingDateTime")
        Date chippingDateTime,

        @NotNull
        @Positive
        @JsonProperty("chipperId")
        Integer chipperId,

        @NotNull
        @Positive
        @JsonProperty("chippingLocationId")
        Long chippingLocationId,

        @JsonProperty("visitedLocations")
        List<Long> visitedLocations,

        @JsonProperty("deathDateTime")
        Date deathDateTime
) {}
