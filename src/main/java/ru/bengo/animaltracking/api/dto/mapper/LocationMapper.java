package ru.bengo.animaltracking.api.dto.mapper;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.bengo.animaltracking.api.dto.LocationDto;
import ru.bengo.animaltracking.store.entity.Location;

@Component
@RequiredArgsConstructor
public class LocationMapper {

    private final ModelMapper modelMapper;

    public LocationDto toDto(Location location) {
        return modelMapper.map(location, LocationDto.class);
    }

    public Location toEntity(LocationDto locationDto) {
        return modelMapper.map(locationDto, Location.class);
    }
}
