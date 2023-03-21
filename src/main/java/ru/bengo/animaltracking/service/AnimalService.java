package ru.bengo.animaltracking.service;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import ru.bengo.animaltracking.dto.AnimalDto;
import ru.bengo.animaltracking.model.Animal;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AnimalService {

    Optional<Animal> findById(@NotNull @Positive Integer id);
    Optional<List<Animal>> search(LocalDateTime startDateTime, LocalDateTime endDateTime,
                                  @Positive Integer chipperId, @Positive Long chippingLocationId,
                                  String lifeStatus, String gender,
                                  @Min(0) Integer from, @Min(1) Integer size);
    Animal addAnimal(@Valid AnimalDto animalDto);
}
