package ru.bengo.animaltracking.service.impl;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import ru.bengo.animaltracking.dto.LocationDto;
import ru.bengo.animaltracking.entity.Location;
import ru.bengo.animaltracking.exception.*;
import ru.bengo.animaltracking.model.Message;
import ru.bengo.animaltracking.repository.LocationRepository;
import ru.bengo.animaltracking.service.LocationService;

@Service
@Validated
@RequiredArgsConstructor
@Slf4j
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;

    @Override
    public Location create(@Valid LocationDto locationDto) throws ConflictException {
        if (isLocationWithLatitudeAndLongitudeExist(locationDto.latitude(), locationDto.longitude())) {
            throw new ConflictException(Message.LOCATION_EXIST.getInfo());
        }

        return locationRepository.save(convertToEntity(locationDto, new Location()));
    }

    @Override
    public Location get(@NotNull @Positive Long id) throws NotFoundException {
        return locationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Message.LOCATION_NOT_FOUND.getInfo()));
    }

    @Override
    public Location update(@Valid LocationDto locationDto, @NotNull @Positive Long id) throws ConflictException, NotFoundException {
        var location = get(id);

        if (isLocationWithLatitudeAndLongitudeExist(locationDto.latitude(), locationDto.longitude())) {
            throw new ConflictException(Message.LOCATION_EXIST.getInfo());
        }

        return locationRepository.save(convertToEntity(locationDto, location));
    }

    @Override
    public void delete(@NotNull @Positive Long id) throws NotFoundException, BadRequestException {
        var location = get(id);

        if (!location.getAnimals().isEmpty()) {
            throw new BadRequestException(Message.LOCATION_ASSOCIATION.getInfo());
        }

        locationRepository.deleteById(id);
    }

    private boolean isLocationWithLatitudeAndLongitudeExist(Double latitude, Double longitude) {
        return locationRepository.findByLatitudeAndLongitude(latitude, longitude).isPresent();
    }

    private Location convertToEntity(LocationDto locationDto, Location location) {
        location.setLatitude(locationDto.latitude());
        location.setLongitude(locationDto.longitude());
        return location;
    }
}
