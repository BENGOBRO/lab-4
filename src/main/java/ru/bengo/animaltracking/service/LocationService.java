package ru.bengo.animaltracking.service;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import ru.bengo.animaltracking.dto.LocationDto;
import ru.bengo.animaltracking.exception.*;
import ru.bengo.animaltracking.entity.Location;

public interface LocationService {
    Location create(@Valid LocationDto locationDto) throws ConflictException;
    Location get(@NotNull @Positive Long id) throws NotFoundException;
    Location update(@Valid LocationDto locationDto, @NotNull @Positive Long id) throws ConflictException, NotFoundException;
    void delete(@NotNull @Positive Long id) throws NotFoundException, BadRequestException;
}
