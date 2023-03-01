package ru.bengo.animaltracking.service;

import ru.bengo.animaltracking.model.Location;

import java.util.Optional;

public interface LocationService {

    Optional<Location> getLocation(Long id);
    Location addLocation(Location location);
    void deleteLocation(Long id);
    Location changeLocation(Location location, Long id);
}
