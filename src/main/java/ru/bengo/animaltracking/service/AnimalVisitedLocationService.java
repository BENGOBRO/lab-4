package ru.bengo.animaltracking.service;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import ru.bengo.animaltracking.dto.AnimalVisitedLocationDto;
import ru.bengo.animaltracking.entity.AnimalVisitedLocation;
import ru.bengo.animaltracking.exception.AnimalNotFoundException;
import ru.bengo.animaltracking.exception.LocationNotFoundException;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface AnimalVisitedLocationService {
    AnimalVisitedLocation create(@NotNull @Positive Long animalId, @NotNull @Positive Long locationId) throws LocationNotFoundException, AnimalNotFoundException;

    List<AnimalVisitedLocation> search(@NotNull @Positive Long animalId, Date startDateTime, Date endDateTime,
                                       @Min(0) Integer from, @Min(1) Integer size);

    AnimalVisitedLocation update(@NotNull @Positive Long animalId, AnimalVisitedLocationDto animalVisitedLocationDto);

    void delete(@NotNull @Positive Long animalId, @NotNull @Positive Long locationId);


}
