package ru.bengo.animaltracking.service.impl;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import ru.bengo.animaltracking.api.dto.LocationDto;
import ru.bengo.animaltracking.api.dto.mapper.LocationMapper;
import ru.bengo.animaltracking.api.exception.BadRequestException;
import ru.bengo.animaltracking.api.exception.ConflictException;
import ru.bengo.animaltracking.api.exception.NotFoundException;
import ru.bengo.animaltracking.store.entity.Location;
import ru.bengo.animaltracking.api.model.Message;
import ru.bengo.animaltracking.store.repository.LocationRepository;
import ru.bengo.animaltracking.service.LocationService;

@Service
@Validated
@RequiredArgsConstructor
@Slf4j
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;
    private final LocationMapper locationMapper;

    @Override
    public Location create(@Valid LocationDto locationDto) {
        if (isLocationWithLatitudeAndLongitudeExist(locationDto.getLatitude(), locationDto.getLongitude())) {
            throw new ConflictException(Message.LOCATION_EXIST.getInfo());
        }

        return locationRepository.save(locationMapper.toEntity(locationDto));
    }

    @Override
    public Location get(@NotNull @Positive Long id) {
        return locationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Message.LOCATION_NOT_FOUND.getInfo()));
    }

    @Override
    public Location update(@Valid LocationDto locationDto, @NotNull @Positive Long id) {
        var location = get(id);

        if (isLocationWithLatitudeAndLongitudeExist(locationDto.getLatitude(), locationDto.getLongitude())) {
            throw new ConflictException(Message.LOCATION_EXIST.getInfo());
        }

        location.setLatitude(locationDto.getLatitude());
        location.setLongitude(locationDto.getLongitude());
        return locationRepository.save(location);
    }

    @Override
    public void delete(@NotNull @Positive Long id) {
        var location = get(id);

        if (!location.getAnimals().isEmpty()) {
            throw new BadRequestException(Message.LOCATION_ASSOCIATION.getInfo());
        }

        locationRepository.deleteById(id);
    }

    private boolean isLocationWithLatitudeAndLongitudeExist(Double latitude, Double longitude) {
        return locationRepository.findByLatitudeAndLongitude(latitude, longitude).isPresent();
    }
}
