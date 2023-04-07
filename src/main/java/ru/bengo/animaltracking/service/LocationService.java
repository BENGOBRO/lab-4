package ru.bengo.animaltracking.service;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import ru.bengo.animaltracking.exception.LocationAlreadyExistException;
import ru.bengo.animaltracking.model.Location;

import java.util.Optional;

public interface LocationService {

    Optional<Location> getLocation(@NotNull @Positive Long id);
    Location addLocation(@Valid Location location) throws LocationAlreadyExistException;
    Long deleteLocation(@NotNull @Positive Long id);
    Location updateLocation(@Valid Location location, @NotNull @Positive Long id) throws LocationAlreadyExistException;
}
