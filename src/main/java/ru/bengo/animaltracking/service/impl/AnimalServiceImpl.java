package ru.bengo.animaltracking.service.impl;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import ru.bengo.animaltracking.dto.AnimalDto;
import ru.bengo.animaltracking.model.Animal;
import ru.bengo.animaltracking.repository.AnimalRepository;
import ru.bengo.animaltracking.service.AnimalService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Validated
public class AnimalServiceImpl implements AnimalService {

    @Autowired
    private AnimalRepository animalRepository;

    @Override
    public Optional<Animal> findById(@NotNull @Positive Integer id) {
        return animalRepository.findById(id);
    }

    @Override
    public Optional<List<Animal>> search(LocalDateTime startDateTime, LocalDateTime endDateTime,
                                         @Positive Integer chipperId, @Positive Long chippingLocationId,
                                         String lifeStatus, String gender,
                                         @Min(0) Integer from, @Min(1) Integer size) {
        return Optional.empty();
    }

    @Override
    public Animal addAnimal(@Valid AnimalDto animalDto) {
        Animal animal = convertToEntity(animalDto);
        return animalRepository.save(animal);
    }

    private Animal convertToEntity(AnimalDto animalDto) {
        return new Animal();
    }
}
