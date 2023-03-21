package ru.bengo.animaltracking.service;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import ru.bengo.animaltracking.model.AnimalType;

import java.util.Optional;

public interface AnimalTypeService {

    Optional<AnimalType> getAnimalTypeById(@NotNull @Positive Long id);

    AnimalType addAnimalType(@Valid AnimalType animalType);
}
