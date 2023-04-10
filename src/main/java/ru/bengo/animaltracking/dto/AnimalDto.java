package ru.bengo.animaltracking.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;
import ru.bengo.animaltracking.annotation.GenderAnnotation;
import ru.bengo.animaltracking.annotation.LifeStatusAnnotation;

import java.util.List;

@Data
public class AnimalDto {

    private List<Long> animalTypes;

    @NotNull
    @Positive
    private Float weight;

    @NotNull
    @Positive
    private Float length;

    @NotNull
    @Positive
    private Float height;

    @NotNull
    @GenderAnnotation
    private String gender;

    @NotNull
    @LifeStatusAnnotation
    private String lifeStatus;

    @NotNull
    @Positive
    private Integer chipperId;

    @NotNull
    @Positive
    private Long ChippingLocationId;

}
