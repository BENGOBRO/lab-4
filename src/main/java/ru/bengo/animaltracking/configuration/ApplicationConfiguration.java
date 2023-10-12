package ru.bengo.animaltracking.configuration;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.bengo.animaltracking.dto.AnimalDto;
import ru.bengo.animaltracking.dto.VisitedLocationDto;
import ru.bengo.animaltracking.entity.Animal;
import ru.bengo.animaltracking.entity.VisitedLocation;

@Configuration
public class ApplicationConfiguration {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        TypeMap<VisitedLocation, VisitedLocationDto> propertyMapperAnimalLocation = modelMapper.createTypeMap(VisitedLocation.class, VisitedLocationDto.class);
        propertyMapperAnimalLocation.addMappings(
                x -> x.map(src -> src.getLocation().getId(), VisitedLocationDto::setLocationPointId)
        );

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

        return modelMapper;
    }

}
