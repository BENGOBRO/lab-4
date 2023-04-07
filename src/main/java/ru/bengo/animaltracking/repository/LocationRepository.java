package ru.bengo.animaltracking.repository;

import org.springframework.data.repository.CrudRepository;
import ru.bengo.animaltracking.model.Location;

import java.util.Optional;

public interface LocationRepository extends CrudRepository<Location, Long> {
    Optional<Location> findByLatitudeAndLongitude(Double latitude, Double longitude);

    Long deleteLocationById(Long id);

}
