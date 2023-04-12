package ru.bengo.animaltracking.service.impl;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import ru.bengo.animaltracking.dto.LocationDto;
import ru.bengo.animaltracking.exception.LocationAlreadyExistException;
import ru.bengo.animaltracking.model.Location;
import ru.bengo.animaltracking.model.Message;
import ru.bengo.animaltracking.repository.LocationRepository;
import ru.bengo.animaltracking.service.LocationService;

import java.util.Optional;

@Service
@Validated
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;

    @Override
    public Optional<Location> getLocation(@NotNull @Positive Long id) {
        return locationRepository.findById(id);
    }

    @Override
    public Location addLocation(@Valid LocationDto locationDto) throws LocationAlreadyExistException {
        if (isLocationWithLatitudeAndLongitudeExist(locationDto.latitude(), locationDto.longitude())) {
            throw new LocationAlreadyExistException(Message.LOCATION_EXIST.getInfo());
        }
        return locationRepository.save(convertToEntity(locationDto));
    }

    @Override
    public Long deleteLocation(@NotNull @Positive Long id) {
        return locationRepository.deleteLocationById(id);
    }

    @Override
    public Location updateLocation(@Valid LocationDto locationDto, @NotNull @Positive Long id) throws LocationAlreadyExistException {
        Optional<Location> foundLocation = locationRepository.findById(id);

        if (foundLocation.isPresent()) {
            if (isLocationWithLatitudeAndLongitudeExist(locationDto.latitude(), locationDto.longitude())) {
                throw new LocationAlreadyExistException(Message.LOCATION_EXIST.getInfo());
            }
            Location location = convertToEntity(locationDto);
            location.setId(id);
            return locationRepository.save(location);
        }

        return null;
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
