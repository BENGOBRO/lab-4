package ru.bengo.animaltracking.api.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TypeDto {
    @NotNull
    @Positive
    Long oldTypeId;

    @NotNull
    @Positive
    Long newTypeId;
}


