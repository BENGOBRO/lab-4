package ru.bengo.animaltracking.api.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LocationDto {
    Long id;

    @NotNull
    @Min(-90)
    @Max(90)
    Double latitude;

    @NotNull
    @Min(-180)
    @Max(180)
    Double longitude;
}

