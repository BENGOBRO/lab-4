package ru.bengo.animaltracking.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.bengo.animaltracking.dto.VisitedLocationDto;
import ru.bengo.animaltracking.dto.VisitedLocationUpdateDto;
import ru.bengo.animaltracking.entity.VisitedLocation;
import ru.bengo.animaltracking.exception.BadRequestException;
import ru.bengo.animaltracking.exception.NotFoundException;
import ru.bengo.animaltracking.service.VisitedLocationService;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/animals/{animalId}/locations")
@RequiredArgsConstructor
@Slf4j
public class VisitedLocationController {

    private final VisitedLocationService visitedLocationService;
    private final ModelMapper modelMapper;

    @PostMapping("/{pointId}")
    public ResponseEntity<VisitedLocationDto> createVisitedLocation(@PathVariable Long animalId,
                                                                          @PathVariable Long pointId) throws NotFoundException, BadRequestException {
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToDto(visitedLocationService.create(animalId, pointId)));
    }

    @GetMapping()
    public ResponseEntity<List<VisitedLocationDto>> searchVisitedLocations(
            @PathVariable Long animalId,
            @RequestParam(required = false) Date startDateTime,
            @RequestParam(required = false) Date endDateTime,
            @RequestParam(defaultValue = "0") Integer from,
            @RequestParam(defaultValue = "10") Integer size) {
        List<VisitedLocation> visitedLocations = visitedLocationService.search(animalId, startDateTime,
                endDateTime, from, size);
        return ResponseEntity.ok(visitedLocations.stream().map(this::convertToDto).toList());
    }

    @PutMapping()
    public ResponseEntity<VisitedLocationDto> updateVisitedLocation(
            @PathVariable Long animalId,
            @RequestBody VisitedLocationUpdateDto visitedLocationUpdateDto) throws NotFoundException, BadRequestException {
        return ResponseEntity.ok(convertToDto(visitedLocationService.update(animalId, visitedLocationUpdateDto)));
    }

    @DeleteMapping("/{visitedPointId}")
    public ResponseEntity<?> deleteVisitedLocation(@PathVariable Long animalId, @PathVariable Long visitedPointId) throws NotFoundException {
        visitedLocationService.delete(animalId, visitedPointId);
        return ResponseEntity.ok().build();
    }


    private VisitedLocationDto convertToDto(VisitedLocation visitedLocation) {
        return modelMapper.map(visitedLocation, VisitedLocationDto.class);
    }
}
