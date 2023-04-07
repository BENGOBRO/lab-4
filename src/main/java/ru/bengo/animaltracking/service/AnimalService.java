package ru.bengo.animaltracking.service;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import ru.bengo.animaltracking.dto.AnimalDto;
import ru.bengo.animaltracking.exception.AnimalTypeNotFoundException;
import ru.bengo.animaltracking.exception.AnimalTypesHasDuplicatesException;
import ru.bengo.animaltracking.exception.ChipperIdNotFoundException;
import ru.bengo.animaltracking.exception.ChippingLocationIdNotFound;
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
    Animal add(@Valid AnimalDto animalDto) throws AnimalTypesHasDuplicatesException, AnimalTypeNotFoundException, ChipperIdNotFoundException, ChippingLocationIdNotFound;

    Animal update(@NotNull @Positive Long id, @Valid Animal animal);
}
