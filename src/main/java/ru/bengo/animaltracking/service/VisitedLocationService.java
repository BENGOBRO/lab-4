package ru.bengo.animaltracking.service;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import ru.bengo.animaltracking.dto.VisitedLocationUpdateDto;
import ru.bengo.animaltracking.entity.VisitedLocation;
import ru.bengo.animaltracking.exception.BadRequestException;
import ru.bengo.animaltracking.exception.NotFoundException;

import java.util.Date;
import java.util.List;

public interface VisitedLocationService {
    VisitedLocation create(@NotNull @Positive Long animalId, @NotNull @Positive Long locationId) throws NotFoundException, BadRequestException;

    List<VisitedLocation> search(@NotNull @Positive Long animalId, Date startDateTime, Date endDateTime,
                                 @Min(0) Integer from, @Min(1) Integer size);

    VisitedLocation update(@NotNull @Positive Long animalId, @Valid VisitedLocationUpdateDto visitedLocationUpdateDto) throws NotFoundException, BadRequestException;

    void delete(@NotNull @Positive Long animalId, @NotNull @Positive Long visitedLocationId) throws NotFoundException;


}
