package ru.bengo.animaltracking.configuration;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.bengo.animaltracking.dto.AnimalDto;
import ru.bengo.animaltracking.dto.AnimalVisitedLocationDto;
import ru.bengo.animaltracking.entity.Animal;
import ru.bengo.animaltracking.entity.AnimalVisitedLocation;

@Configuration
public class ApplicationConfiguration {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        TypeMap<AnimalVisitedLocation, AnimalVisitedLocationDto> propertyMapperAnimalLocation = modelMapper.createTypeMap(AnimalVisitedLocation.class, AnimalVisitedLocationDto.class);
        propertyMapperAnimalLocation.addMappings(
                x -> x.map(src -> src.getLocation().getId(), AnimalVisitedLocationDto::setLocationPointId)
        );

        TypeMap<Animal, AnimalDto> propertyMapperAnimal = modelMapper.createTypeMap(Animal.class, AnimalDto.class);
        propertyMapperAnimal.addMappings(
                x -> x.skip(AnimalDto::setAnimalTypes)
        );
        propertyMapperAnimal.addMappings(
                x -> x.skip(AnimalDto::setVisitedLocations)
        );
        propertyMapperAnimal.addMappings(
                x -> x.map(src -> src.getChipper().getId(), AnimalDto::setChipperId)
        );
        propertyMapperAnimal.addMappings(
                x -> x.map(src -> src.getChippingLocation().getId(), AnimalDto::setChippingLocationId)
        );

        return modelMapper;
    }

}
