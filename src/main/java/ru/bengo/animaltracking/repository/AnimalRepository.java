package ru.bengo.animaltracking.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.bengo.animaltracking.model.Animal;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AnimalRepository extends CrudRepository<Animal, Long> {
    @Query(value =
            "select * from animals " +
            "where (CAST(?1 AS timestamp) is null or chipping_date_time > CAST(?1 AS timestamp)) and (CAST(?2 AS timestamp) is null or death_date_time < CAST(?2 AS timestamp)) and (?3 is null or chipper_id = ?3) and (?4 is null or chipping_location_id = ?4) and (?5 is null or life_status = ?5) and (?6 is null or gender = ?6) " +
            "order by id", nativeQuery = true)
    List<Animal> search(LocalDateTime chippingDateTime, LocalDateTime deathDateTime, Integer chipperId, Long chippingLocationId, String lifeStatus, String gender, Pageable pageable);
}
