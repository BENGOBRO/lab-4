package ru.bengo.animaltracking.service;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.security.core.parameters.P;
import ru.bengo.animaltracking.dto.AnimalDto;
import ru.bengo.animaltracking.dto.TypeDto;
import ru.bengo.animaltracking.exception.*;
import ru.bengo.animaltracking.model.Animal;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AnimalService {

    Optional<Animal> findById(@NotNull @Positive Long id);
    List<Animal> search(LocalDateTime startDateTime, LocalDateTime endDateTime,
                                  @Positive Integer chipperId, @Positive Long chippingLocationId,
                                  String lifeStatus, String gender,
                                  @Min(0) Integer from, @Min(1) Integer size);
    Animal create(@Valid AnimalDto animalDto) throws AnimalTypesHasDuplicatesException, AnimalTypeNotFoundException, ChipperIdNotFoundException, ChippingLocationIdNotFound;

    Animal update(@NotNull @Positive Long id, @Valid AnimalDto animal) throws AnimalNotFoundException,
            UpdateDeadToAliveException, ChipperIdNotFoundException, ChippingLocationIdNotFound,
            NewChippingLocationIdEqualsFirstVisitedLocationIdException;

    void delete(@NotNull @Positive Long id) throws AnimalNotFoundException;

    Animal addAnimalTypeToAnimal(@NotNull @Positive Long animalId, @NotNull @Positive Long typeId) throws AnimalNotFoundException,
            AnimalTypeNotFoundException, AnimalTypesContainNewAnimalTypeException;

    Animal updateAnimalTypesInAnimal(@NotNull @Positive Long animalId, @Valid TypeDto typeDto) throws AnimalNotFoundException, AnimalTypeNotFoundException, AnimalDoesNotHaveTypeException, AnimalTypeAlreadyExist;
}
