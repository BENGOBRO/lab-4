package ru.bengo.animaltracking.service;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import ru.bengo.animaltracking.dto.AnimalDto;
import ru.bengo.animaltracking.dto.TypeDto;
import ru.bengo.animaltracking.exception.*;

import java.time.LocalDateTime;
import java.util.List;

public interface AnimalService {
    AnimalDto create(@Valid AnimalDto animalDto)
            throws AnimalTypesHasDuplicatesException, AnimalTypeNotFoundException,
            ChipperIdNotFoundException, ChippingLocationIdNotFound;

    AnimalDto get(@NotNull @Positive Long id) throws AnimalNotFoundException;

    AnimalDto update(@NotNull @Positive Long id, @Valid AnimalDto animal) throws AnimalNotFoundException,
            UpdateDeadToAliveException, ChipperIdNotFoundException, ChippingLocationIdNotFound,
            NewChippingLocationIdEqualsFirstVisitedLocationIdException;

    void delete(@NotNull @Positive Long id) throws AnimalNotFoundException;

    List<AnimalDto> search(LocalDateTime startDateTime, LocalDateTime endDateTime,
                        @Positive Integer chipperId, @Positive Long chippingLocationId,
                        String lifeStatus, String gender,
                        @Min(0) Integer from, @Min(1) Integer size);

    AnimalDto addAnimalTypeToAnimal(@NotNull @Positive Long animalId, @NotNull @Positive Long typeId) throws AnimalNotFoundException,
            AnimalTypeNotFoundException, AnimalTypesContainNewAnimalTypeException;

    AnimalDto updateAnimalTypesInAnimal(@NotNull @Positive Long animalId, @Valid TypeDto typeDto) throws AnimalNotFoundException, AnimalTypeNotFoundException, AnimalDoesNotHaveTypeException, AnimalTypeAlreadyExist;
}
