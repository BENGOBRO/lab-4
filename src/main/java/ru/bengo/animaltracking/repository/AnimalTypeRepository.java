package ru.bengo.animaltracking.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.bengo.animaltracking.model.AnimalType;

@Repository
public interface AnimalTypeRepository extends CrudRepository<AnimalType, Long> {
}
