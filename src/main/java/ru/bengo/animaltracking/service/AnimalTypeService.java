package ru.bengo.animaltracking.service;

import ru.bengo.animaltracking.model.AnimalType;

import java.util.Optional;

public interface AnimalTypeService {

    Optional<AnimalType> getAnimalTypeById(Long id);

    AnimalType addAnimalType(AnimalType animalType);
}
