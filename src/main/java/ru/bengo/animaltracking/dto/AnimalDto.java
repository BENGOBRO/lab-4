package ru.bengo.animaltracking.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import ru.bengo.animaltracking.annotation.GenderAnnotation;
import ru.bengo.animaltracking.annotation.LifeStatusAnnotation;

import java.util.Date;
import java.util.List;


@Data
public class AnimalDto {
        private Long id;

        @JsonProperty("animalTypes")
        private List<Long> animalTypesIds;

        @NotNull
        @Positive
        @JsonProperty("weight")
        private Double weight;

        @NotNull
        @Positive
        @JsonProperty("length")
        private Double length;

        @NotNull
        @Positive
        @JsonProperty("height")
        private Double height;

        @NotNull
        @GenderAnnotation
        @JsonProperty("gender")
        private String gender;

        @LifeStatusAnnotation
        @JsonProperty("lifeStatus")
        private String lifeStatus;

        @JsonProperty("chippingDateTime")
        private Date chippingDateTime;

        @NotNull
        @Positive
        @JsonProperty("chipperId")
        private Integer chipperId;

        @NotNull
        @Positive
        @JsonProperty("chippingLocationId")
        private Long chippingLocationId;

        @JsonProperty("visitedLocations")
        private List<Long> visitedLocationsIds;

        @JsonProperty("deathDateTime")
        private Date deathDateTime;
}
