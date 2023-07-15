package ru.bengo.animaltracking.service;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import ru.bengo.animaltracking.dto.LocationDto;
import ru.bengo.animaltracking.exception.LocationAlreadyExistException;
import ru.bengo.animaltracking.exception.LocationNotFoundException;
import ru.bengo.animaltracking.model.Location;

import java.util.Optional;

public interface LocationService {
    Location create(@Valid LocationDto locationDto) throws LocationAlreadyExistException;
    Location get(@NotNull @Positive Long id) throws LocationNotFoundException;
    Location update(@Valid LocationDto locationDto, @NotNull @Positive Long id) throws LocationAlreadyExistException, LocationNotFoundException;
    void delete(@NotNull @Positive Long id) throws LocationNotFoundException;
}
