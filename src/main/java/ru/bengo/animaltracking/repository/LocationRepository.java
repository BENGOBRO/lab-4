package ru.bengo.animaltracking.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.bengo.animaltracking.entity.Location;

import java.util.Optional;

@Repository
public interface LocationRepository extends CrudRepository<Location, Long> {
    Optional<Location> findByLatitudeAndLongitude(Double latitude, Double longitude);
}
