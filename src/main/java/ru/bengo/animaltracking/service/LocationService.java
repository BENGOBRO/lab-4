package ru.bengo.animaltracking.service;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import ru.bengo.animaltracking.dto.LocationDto;
import ru.bengo.animaltracking.exception.LocationAlreadyExistException;
import ru.bengo.animaltracking.model.Location;

import java.util.Optional;

public interface LocationService {

    Optional<Location> getLocation(@NotNull @Positive Long id);
    Location addLocation(@Valid LocationDto locationDto) throws LocationAlreadyExistException;
    Long deleteLocation(@NotNull @Positive Long id);
    Location updateLocation(@Valid LocationDto locationDto, @NotNull @Positive Long id) throws LocationAlreadyExistException;
}
