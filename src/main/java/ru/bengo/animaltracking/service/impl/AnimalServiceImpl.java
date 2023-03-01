package ru.bengo.animaltracking.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.bengo.animaltracking.dto.AnimalDto;
import ru.bengo.animaltracking.model.Animal;
import ru.bengo.animaltracking.repository.AnimalRepository;
import ru.bengo.animaltracking.service.AnimalService;

import java.util.List;
import java.util.Optional;

@Service
public class AnimalServiceImpl implements AnimalService {

    @Autowired
    private AnimalRepository animalRepository;

    @Override
    public Optional<Animal> findById(Integer id) {
        return animalRepository.findById(id);
    }

    @Override
    public Optional<List<Animal>> search(String startDateTime, String endDateTime, Integer chipperId, Long chippingLocationId, String lifeStatus, String gender, Integer from, Integer size) {
        return Optional.empty();
    }

    @Override
    public Animal addAnimal(AnimalDto animalDto) {
        Animal animal = convertToEntity(animalDto);
        return animalRepository.save(animal);
    }

    private Animal convertToEntity(AnimalDto animalDto) {
        return new Animal();
    }
}
