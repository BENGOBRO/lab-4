package ru.bengo.animaltracking.api.dto.mapper;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;
import ru.bengo.animaltracking.api.dto.AnimalDto;
import ru.bengo.animaltracking.store.entity.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AnimalMapper {

    private final ModelMapper modelMapper;

    public AnimalDto toDto(Animal animal) {
        List<Long> animalTypes = animal.getAnimalTypes().stream().map(AnimalType::getId).toList();
        List<Long> visitedLocations = Optional.ofNullable(animal.getVisitedLocations()).orElse(new ArrayList<>()).stream().map(VisitedLocation::getId).toList();
        AnimalDto animalDto = modelMapper.map(animal, AnimalDto.class);
        animalDto.setAnimalTypesIds(animalTypes);
        animalDto.setVisitedLocationsIds(visitedLocations);
        return animalDto;
    }

    public Animal toEntity(AnimalDto animalDto, Location chippingLocation,
                           Account chipper, List<AnimalType> animalTypes) {
        Animal animal = modelMapper.map(animalDto, Animal.class);
        animal.setChippingLocation(chippingLocation);
        animal.setChipper(chipper);
        animal.setAnimalTypes(animalTypes);
        return animal;
    }

    @PostConstruct
    public void setupMapper() {
        TypeMap<Animal, AnimalDto> propertyMapperAnimal = modelMapper.createTypeMap(Animal.class, AnimalDto.class);
        propertyMapperAnimal.addMappings(
                x -> x.skip(AnimalDto::setAnimalTypesIds)
        );
        propertyMapperAnimal.addMappings(
                x -> x.skip(AnimalDto::setVisitedLocationsIds)
        );
        propertyMapperAnimal.addMappings(
                x -> x.map(src -> src.getChipper().getId(), AnimalDto::setChipperId)
        );
        propertyMapperAnimal.addMappings(
                x -> x.map(src -> src.getChippingLocation().getId(), AnimalDto::setChippingLocationId)
        );
    }
}
