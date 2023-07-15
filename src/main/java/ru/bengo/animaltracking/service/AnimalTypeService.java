package ru.bengo.animaltracking.service;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import ru.bengo.animaltracking.exception.AnimalTypeAlreadyExist;
import ru.bengo.animaltracking.model.AnimalType;

import java.util.Optional;

public interface AnimalTypeService {
    AnimalType create(@Valid AnimalType animalType) throws AnimalTypeAlreadyExist;

    Optional<AnimalType> get(@NotNull @Positive Long id);

    AnimalType update(@NotNull @Positive Long id, @Valid AnimalType animalType) throws AnimalTypeAlreadyExist;

    void delete(@NotNull @Positive Long id);
}
