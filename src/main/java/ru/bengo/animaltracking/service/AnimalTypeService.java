package ru.bengo.animaltracking.service;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import ru.bengo.animaltracking.dto.AnimalTypeDto;
import ru.bengo.animaltracking.entity.AnimalType;
import ru.bengo.animaltracking.exception.ConflictException;
import ru.bengo.animaltracking.exception.NotFoundException;

public interface AnimalTypeService {
    AnimalType create(@Valid AnimalTypeDto animalType) throws ConflictException;

    AnimalType get(@NotNull @Positive Long id) throws NotFoundException;

    AnimalType update(@NotNull @Positive Long id, @Valid AnimalTypeDto animalType) throws NotFoundException, ConflictException;

    void delete(@NotNull @Positive Long id) throws NotFoundException;
}
