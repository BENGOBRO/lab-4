package ru.bengo.animaltracking.service;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import ru.bengo.animaltracking.api.dto.AnimalDto;
import ru.bengo.animaltracking.api.dto.TypeDto;
import ru.bengo.animaltracking.store.entity.Animal;

import java.time.LocalDateTime;
import java.util.List;

public interface AnimalService {
    Animal create(@Valid AnimalDto animalDto);

    Animal get(@NotNull @Positive Long animalId);

    Animal update(@NotNull @Positive Long animalId, AnimalDto animal);

    void delete(@NotNull @Positive Long animalId);

    List<Animal> search(LocalDateTime startDateTime, LocalDateTime endDateTime,
                        @Positive Integer chipperId, @Positive Long chippingLocationId,
                        String lifeStatus, String gender,
                        @Min(0) Integer from, @Min(1) Integer size);

    Animal addAnimalTypeToAnimal(@NotNull @Positive Long animalId, @NotNull @Positive Long typeId);

    Animal updateAnimalTypeInAnimal(@NotNull @Positive Long animalId, TypeDto typeDto);

    Animal deleteAnimalTypeInAnimal(@NotNull @Positive Long animalId, @NotNull @Positive Long typeId);
}

