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

    Location getLocation(@NotNull @Positive Long id) throws LocationNotFoundException;
    Location addLocation(@Valid LocationDto locationDto) throws LocationAlreadyExistException;
    void deleteLocation(@NotNull @Positive Long id) throws LocationNotFoundException;
    Location updateLocation(@Valid LocationDto locationDto, @NotNull @Positive Long id) throws LocationAlreadyExistException;
}
