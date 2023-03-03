package ru.bengo.animaltracking.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.bengo.animaltracking.model.AnimalType;
import ru.bengo.animaltracking.repository.AnimalTypeRepository;
import ru.bengo.animaltracking.service.AnimalTypeService;

import java.util.Optional;

@Service
public class AnimalTypeServiceImpl implements AnimalTypeService {

    @Autowired
    private AnimalTypeRepository animalTypeRepository;

    @Override
    public Optional<AnimalType> getAnimalTypeById(Long id) {
        return animalTypeRepository.findById(id);
    }

    @Override
    public AnimalType addAnimalType(AnimalType animalType) {
        return animalTypeRepository.save(animalType);
    }
}
