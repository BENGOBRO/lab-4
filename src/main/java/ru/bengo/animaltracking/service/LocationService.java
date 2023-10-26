package ru.bengo.animaltracking.service;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import ru.bengo.animaltracking.api.dto.LocationDto;
import ru.bengo.animaltracking.store.entity.Location;

public interface LocationService {
    Location create(@Valid LocationDto locationDto);
    Location get(@NotNull @Positive Long id);
    Location update(@Valid LocationDto locationDto, @NotNull @Positive Long id);
    void delete(@NotNull @Positive Long id);
}
