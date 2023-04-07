package ru.bengo.animaltracking.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import ru.bengo.animaltracking.model.Animal;
import ru.bengo.animaltracking.model.Gender;
import ru.bengo.animaltracking.model.LifeStatus;

import java.time.LocalDateTime;
import java.util.Optional;

public interface AnimalRepository extends CrudRepository<Animal, Long> {

    Page<Animal> findByChippingDateTimeContainingOrDeathDateTimeContainingOrChipperIdContainingOrLifeStatusContainingOrGenderContaining(LocalDateTime chippingDateTime, LocalDateTime deathDateTime, Integer chipperId, LifeStatus lifeStatus, Gender gender, Pageable pageable);

}
