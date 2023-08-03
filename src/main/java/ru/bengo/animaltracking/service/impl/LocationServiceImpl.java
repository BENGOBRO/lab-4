package ru.bengo.animaltracking.service.impl;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import ru.bengo.animaltracking.dto.LocationDto;
import ru.bengo.animaltracking.exception.LocationAlreadyExistException;
import ru.bengo.animaltracking.exception.LocationNotFoundException;
import ru.bengo.animaltracking.entity.Location;
import ru.bengo.animaltracking.model.Message;
import ru.bengo.animaltracking.repository.LocationRepository;
import ru.bengo.animaltracking.service.LocationService;

import java.util.Optional;

@Service
@Validated
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;
    private final static Logger log = LoggerFactory.getLogger(LocationServiceImpl.class);

    @Override
    public Location create(@Valid LocationDto locationDto) throws LocationAlreadyExistException {
        if (isLocationWithLatitudeAndLongitudeExist(locationDto.latitude(), locationDto.longitude())) {
            throw new LocationAlreadyExistException(Message.LOCATION_EXIST.getInfo());
        }

        return locationRepository.save(convertToEntity(locationDto));
    }

    @Override
    public Location get(@NotNull @Positive Long id) throws LocationNotFoundException {
        Optional<Location> foundLocation = locationRepository.findById(id);

        if (foundLocation.isEmpty()) {
            throw new LocationNotFoundException(Message.LOCATION_NOT_FOUND.getInfo());
        }

        return foundLocation.get();
    }

    @Override
    public Location update(@Valid LocationDto locationDto, @NotNull @Positive Long id) throws LocationAlreadyExistException, LocationNotFoundException {
        Optional<Location> foundLocation = locationRepository.findById(id);

        if (foundLocation.isEmpty()) {
            throw new LocationNotFoundException(Message.LOCATION_NOT_FOUND.getInfo());
        }
        if (isLocationWithLatitudeAndLongitudeExist(locationDto.latitude(), locationDto.longitude())) {
            throw new LocationAlreadyExistException(Message.LOCATION_EXIST.getInfo());
        }

        Location location = convertToEntity(locationDto);
        location.setId(id);
        return locationRepository.save(location);
    }

    @Override
    public void delete(@NotNull @Positive Long id) throws LocationNotFoundException {
        Optional<Location> foundLocation = locationRepository.findById(id);
        log.warn("HERE");

        if (foundLocation.isEmpty()) {
            throw new LocationNotFoundException(Message.LOCATION_NOT_FOUND.getInfo());
        }

        locationRepository.deleteById(id);
    }

    private boolean isLocationWithLatitudeAndLongitudeExist(Double latitude, Double longitude) {
        return locationRepository.findByLatitudeAndLongitude(latitude, longitude).isPresent();
    }

    private Location convertToEntity(LocationDto locationDto) {
        return Location.builder()
                .latitude(locationDto.latitude())
                .longitude(locationDto.longitude())
                .build();
    }
}
