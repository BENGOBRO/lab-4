package ru.bengo.animaltracking.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class TypeDto {

    @NotNull
    @Positive
    private Long oldTypeId;

    @NotNull
    @Positive
    private Long newTypeId;

}
