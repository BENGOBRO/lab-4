package ru.bengo.animaltracking.service;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import ru.bengo.animaltracking.dto.AnimalDto;
import ru.bengo.animaltracking.dto.TypeDto;
import ru.bengo.animaltracking.entity.Animal;
import ru.bengo.animaltracking.exception.*;

import java.time.LocalDateTime;
import java.util.List;

public interface AnimalService {
    Animal create(@Valid AnimalDto animalDto)
            throws AnimalTypesHasDuplicatesException, AnimalTypeNotFoundException,
             AccountNotFoundException, LocationNotFoundException;

    Animal get(@NotNull @Positive Long id) throws AnimalNotFoundException;

    Animal update(@NotNull @Positive Long id, @Valid AnimalDto animal) throws AnimalNotFoundException,
            UpdateDeadToAliveException,
            NewChippingLocationIdEqualsFirstVisitedLocationIdException, LocationNotFoundException, AnimalTypeNotFoundException, AccountNotFoundException;

    void delete(@NotNull @Positive Long id) throws AnimalNotFoundException;

    List<Animal> search(LocalDateTime startDateTime, LocalDateTime endDateTime,
                        @Positive Integer chipperId, @Positive Long chippingLocationId,
                        String lifeStatus, String gender,
                        @Min(0) Integer from, @Min(1) Integer size);

    Animal addAnimalTypeToAnimal(@NotNull @Positive Long animalId, @NotNull @Positive Long typeId) throws AnimalNotFoundException,
            AnimalTypeNotFoundException, AnimalTypesContainNewAnimalTypeException;

    Animal updateAnimalTypesInAnimal(@NotNull @Positive Long animalId, @Valid TypeDto typeDto) throws AnimalNotFoundException, AnimalTypeNotFoundException, AnimalDoesNotHaveTypeException, AnimalTypeAlreadyExist;
}
