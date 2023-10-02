package ru.bengo.animaltracking.controller;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.bengo.animaltracking.dto.AnimalVisitedLocationDto;
import ru.bengo.animaltracking.entity.AnimalVisitedLocation;
import ru.bengo.animaltracking.exception.AnimalNotFoundException;
import ru.bengo.animaltracking.exception.LocationNotFoundException;
import ru.bengo.animaltracking.service.AnimalVisitedLocationService;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/animals/{animalId}/locations")
@RequiredArgsConstructor
public class AnimalVisitedLocationController {

    private final AnimalVisitedLocationService animalVisitedLocationService;
    private final ModelMapper modelMapper;

    @PostMapping("/{pointId}")
    public ResponseEntity<AnimalVisitedLocationDto> createAnimalVisitedLocation(@PathVariable Long animalId,
                                                                          @PathVariable Long pointId) throws AnimalNotFoundException, LocationNotFoundException {
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToDto(animalVisitedLocationService.create(animalId, pointId)));
    }

    @GetMapping()
    public ResponseEntity<List<AnimalVisitedLocationDto>> searchAnimalVisitedLocations(
            @PathVariable Long animalId,
            @RequestParam(required = false) Date startDateTime,
            @RequestParam(required = false) Date endDateTime,
            @RequestParam(defaultValue = "0") Integer from,
            @RequestParam(defaultValue = "10") Integer size) {
        List<AnimalVisitedLocation> visitedLocations = animalVisitedLocationService.search(animalId, startDateTime,
                endDateTime, from, size);
        return ResponseEntity.ok(visitedLocations.stream().map(this::convertToDto).toList());
    }

    @PutMapping
    public ResponseEntity<AnimalVisitedLocationDto> updateAnimalVisitedLocation(
            @PathVariable Long animalId,
            @RequestBody AnimalVisitedLocationDto animalVisitedLocationDto) {
        return ResponseEntity.ok(convertToDto(animalVisitedLocationService.update(animalId, animalVisitedLocationDto)));
    }

    @DeleteMapping("/{visitedPointId}")
    public ResponseEntity<?> deleteAnimalVisitedLocation(@PathVariable Long animalId, @PathVariable Long visitedPointId) {
        return ResponseEntity.ok().build();
    }


    private AnimalVisitedLocationDto convertToDto(AnimalVisitedLocation visitedLocation) {
//        TypeMap<AnimalVisitedLocation, AnimalVisitedLocationDto> propertyMapper = modelMapper.createTypeMap(AnimalVisitedLocation.class, AnimalVisitedLocationDto.class);
//        propertyMapper.addMappings(
//                modelMapper -> modelMapper.map(src -> src.getLocation().getId(), AnimalVisitedLocationDto::setLocationPointId)
//        );
        return modelMapper.map(visitedLocation, AnimalVisitedLocationDto.class);
    }
}
