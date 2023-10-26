package ru.bengo.animaltracking.api.dto.mapper;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.bengo.animaltracking.api.dto.VisitedLocationDto;
import ru.bengo.animaltracking.store.entity.VisitedLocation;

@Component
@RequiredArgsConstructor
public class VisitedLocationMapper {

    private final ModelMapper modelMapper;

    public VisitedLocationDto toDto(VisitedLocation visitedLocation) {
        return modelMapper.map(visitedLocation, VisitedLocationDto.class);
    }

    public VisitedLocation toEntity(VisitedLocationDto visitedLocationDto) {
        return modelMapper.map(visitedLocationDto, VisitedLocation.class);
    }
}
