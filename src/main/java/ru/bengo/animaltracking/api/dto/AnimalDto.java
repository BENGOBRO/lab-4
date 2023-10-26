package ru.bengo.animaltracking.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import ru.bengo.animaltracking.api.annotation.GenderAnnotation;
import ru.bengo.animaltracking.api.annotation.LifeStatusAnnotation;

import java.util.Date;
import java.util.List;


@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AnimalDto {
        Long id;

        @JsonProperty("animalTypes")
        List<Long> animalTypesIds;

        @NotNull
        @Positive
        @JsonProperty("weight")
        Double weight;

        @NotNull
        @Positive
        @JsonProperty("length")
        Double length;

        @NotNull
        @Positive
        @JsonProperty("height")
        Double height;

        @NotNull
        @GenderAnnotation
        @JsonProperty("gender")
        String gender;

        @LifeStatusAnnotation
        @JsonProperty("lifeStatus")
        String lifeStatus;

        @JsonProperty("chippingDateTime")
        Date chippingDateTime;

        @NotNull
        @Positive
        @JsonProperty("chipperId")
        Integer chipperId;

        @NotNull
        @Positive
        @JsonProperty("chippingLocationId")
        Long chippingLocationId;

        @JsonProperty("visitedLocations")
        List<Long> visitedLocationsIds;

        @JsonProperty("deathDateTime")
        Date deathDateTime;
}
