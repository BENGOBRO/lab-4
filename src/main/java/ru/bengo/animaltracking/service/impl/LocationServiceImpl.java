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

        return locationRepository.save(convertToEntity(locationDto));
    }

    @Override
    public Location get(@NotNull @Positive Long id) throws NotFoundException {
        return locationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Message.LOCATION_NOT_FOUND.getInfo()));
    }

    @Override
    public Location update(@Valid LocationDto locationDto, @NotNull @Positive Long id) throws ConflictException, NotFoundException {
        locationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Message.LOCATION_NOT_FOUND.getInfo()));

        if (isLocationWithLatitudeAndLongitudeExist(locationDto.latitude(), locationDto.longitude())) {
            throw new ConflictException(Message.LOCATION_EXIST.getInfo());
        }

        Location location = convertToEntity(locationDto);
        location.setId(id);
        return locationRepository.save(location);
    }

    @Override
    public void delete(@NotNull @Positive Long id) throws NotFoundException, BadRequestException {
        Location location =locationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Message.LOCATION_NOT_FOUND.getInfo()));

        if (!location.getAnimals().isEmpty()) {
            throw new BadRequestException(Message.LOCATION_ASSOCIATION.getInfo());
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
