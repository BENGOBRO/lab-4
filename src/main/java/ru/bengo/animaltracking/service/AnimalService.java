package ru.bengo.animaltracking.service;

import ru.bengo.animaltracking.dto.AnimalDto;
import ru.bengo.animaltracking.model.Animal;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AnimalService {

    Optional<Animal> findById(Integer id);
    Optional<List<Animal>> search(LocalDateTime startDateTime, LocalDateTime endDateTime,
                                  Integer chipperId, Long chippingLocationId,
                                  String lifeStatus, String gender, Integer from, Integer size);
    Animal addAnimal(AnimalDto animalDto);
}
