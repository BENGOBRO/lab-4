package ru.bengo.animaltracking.service.impl;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import ru.bengo.animaltracking.model.Location;
import ru.bengo.animaltracking.repository.LocationRepository;
import ru.bengo.animaltracking.service.LocationService;

import java.util.Optional;

@Service
@Validated
public class LocationServiceImpl implements LocationService {

    @Autowired
    private LocationRepository locationRepository;

    @Override
    public Optional<Location> getLocation(@NotNull @Positive Long id) {
        return locationRepository.findById(id);
    }

    @Override
    public Location addLocation(@Valid Location location) {
        return locationRepository.save(location);
    }

    @Override
    public void deleteLocation(@NotNull @Positive Long id) {
        locationRepository.deleteById(id);
    }

    @Override
    public Location changeLocation(@Valid Location location, @NotNull @Positive Long id) {
        Optional<Location> foundLocation = locationRepository.findById(id);

        if (foundLocation.isPresent()) {
            return locationRepository.save(location);
        }

        return null;
    }
}
