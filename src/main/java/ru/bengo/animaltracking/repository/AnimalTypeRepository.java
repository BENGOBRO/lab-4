package ru.bengo.animaltracking.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.bengo.animaltracking.model.AnimalType;

import java.util.Optional;

@Repository
public interface AnimalTypeRepository extends CrudRepository<AnimalType, Long> {
    Optional<AnimalType> findByType(String type);
}
