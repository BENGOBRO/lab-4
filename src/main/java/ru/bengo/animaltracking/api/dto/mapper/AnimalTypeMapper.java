package ru.bengo.animaltracking.api.dto.mapper;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.bengo.animaltracking.api.dto.AnimalTypeDto;
import ru.bengo.animaltracking.store.entity.AnimalType;

@Component
@RequiredArgsConstructor
public class AnimalTypeMapper{

    private final ModelMapper modelMapper;

    public AnimalTypeDto toDto(AnimalType animalType) {
        return modelMapper.map(animalType, AnimalTypeDto.class);
    }

    public AnimalType toEntity(AnimalTypeDto animalTypeDto) {
        return modelMapper.map(animalTypeDto, AnimalType.class);
    }
}
