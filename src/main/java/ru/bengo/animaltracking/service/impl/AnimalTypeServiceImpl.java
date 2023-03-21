package ru.bengo.animaltracking.service.impl;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import ru.bengo.animaltracking.model.AnimalType;
import ru.bengo.animaltracking.repository.AnimalTypeRepository;
import ru.bengo.animaltracking.service.AnimalTypeService;

import java.util.Optional;

@Service
@Validated
public class AnimalTypeServiceImpl implements AnimalTypeService {

    @Autowired
    private AnimalTypeRepository animalTypeRepository;

    @Override
    public Optional<AnimalType> getAnimalTypeById(@NotNull @Positive Long id) {
        return animalTypeRepository.findById(id);
    }

    @Override
    public AnimalType addAnimalType(@Valid AnimalType animalType) {
        return animalTypeRepository.save(animalType);
    }
}
