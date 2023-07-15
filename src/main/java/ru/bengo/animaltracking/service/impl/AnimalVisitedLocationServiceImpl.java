package ru.bengo.animaltracking.service.impl;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.bengo.animaltracking.model.AnimalVisitedLocation;
import ru.bengo.animaltracking.repository.AnimalVisitedLocationRepository;
import ru.bengo.animaltracking.service.AnimalVisitedLocationService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnimalVisitedLocationServiceImpl implements AnimalVisitedLocationService {

    private final AnimalVisitedLocationRepository repository;

    @Override
    public List<AnimalVisitedLocation> search(@Positive Long animalId, LocalDateTime startDateTime,
                                              LocalDateTime endDateTime, @Min(0) Integer from, @Min(1) Integer size) {
        List<AnimalVisitedLocation> animalVisitedLocations = repository.search(startDateTime, endDateTime);

        return animalVisitedLocations.stream().skip(from).limit(size).collect(Collectors.toList());
    }
}
