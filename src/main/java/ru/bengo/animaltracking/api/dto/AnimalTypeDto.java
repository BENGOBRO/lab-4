package ru.bengo.animaltracking.api.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AnimalTypeDto {
    Long id;

    @NotBlank
    String type;
}


