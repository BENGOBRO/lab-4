package ru.bengo.animaltracking.repository;

import org.springframework.data.repository.CrudRepository;
import ru.bengo.animaltracking.model.Animal;

import java.util.Optional;

public interface AnimalRepository extends CrudRepository<Animal, Integer> {
}
