package ru.bengo.animaltracking.service;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import ru.bengo.animaltracking.entity.AnimalVisitedLocation;

import java.time.LocalDateTime;
import java.util.List;

public interface AnimalVisitedLocationService {
    List<AnimalVisitedLocation> search(@Positive Long animalId, LocalDateTime startDateTime, LocalDateTime endDateTime,
                                       @Min(0) Integer from, @Min(1) Integer size);
}
