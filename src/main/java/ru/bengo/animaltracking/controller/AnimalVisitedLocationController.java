package ru.bengo.animaltracking.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.bengo.animaltracking.dto.AnimalVisitedLocationDto;
import ru.bengo.animaltracking.model.AnimalVisitedLocation;
import ru.bengo.animaltracking.service.AnimalVisitedLocationService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/animals/{animalId}/locations")
@RequiredArgsConstructor
public class AnimalVisitedLocationController {

    private final AnimalVisitedLocationService animalVisitedLocationService;

    @GetMapping
    public ResponseEntity<List<AnimalVisitedLocation>> searchAnimalVisitedLocations(
            @PathVariable Long animalId,
            @RequestParam(required = false) LocalDateTime startDateTime,
            @RequestParam(required = false) LocalDateTime endDateTime,
            @RequestParam(defaultValue = "0") Integer from,
            @RequestParam(defaultValue = "10") Integer size) {
        return ResponseEntity.ok(animalVisitedLocationService.search(animalId, startDateTime,
                endDateTime, from, size));
    }

    @PostMapping("/{pointId}")
    public ResponseEntity<AnimalVisitedLocation> addAnimalVisitedLocation(@PathVariable Long animalId,
                                                                          @PathVariable Long pointId) {
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<AnimalVisitedLocation> updateAnimalVisitedLocation(
            @PathVariable Long animalId,
            @RequestBody AnimalVisitedLocationDto animalVisitedLocationDto) {
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{visitedPointId}")
    public ResponseEntity<?> deleteAnimalVisitedLocation(@PathVariable Long animalId, @PathVariable Long visitedPointId) {
        return ResponseEntity.ok().build();
    }
}
