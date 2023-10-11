package ru.bengo.animaltracking.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.bengo.animaltracking.dto.LocationDto;
import ru.bengo.animaltracking.entity.Location;
import ru.bengo.animaltracking.exception.BadRequestException;
import ru.bengo.animaltracking.exception.ConflictException;
import ru.bengo.animaltracking.exception.NotFoundException;
import ru.bengo.animaltracking.service.LocationService;

@RestController
@RequestMapping("/locations")
@RequiredArgsConstructor
public class LocationController {

    private final LocationService locationService;

    @PostMapping
    public ResponseEntity<Location> createLocation(@RequestBody LocationDto locationDto) throws ConflictException {
        return ResponseEntity.status(HttpStatus.CREATED).body(locationService.create(locationDto));
    }

    @GetMapping("/{pointId}")
    public ResponseEntity<Location> getLocation(@PathVariable("pointId") Long id) throws NotFoundException {
        return ResponseEntity.ok(locationService.get(id));
    }

    @PutMapping("/{pointId}")
    public ResponseEntity<Location> updateLocation(@RequestBody LocationDto locationDto,
                                                   @PathVariable("pointId") Long id) throws ConflictException, NotFoundException {
        return ResponseEntity.ok(locationService.update(locationDto, id));
    }

    @DeleteMapping("/{pointId}")
    public ResponseEntity<?> deleteLocation(@PathVariable("pointId") Long id) throws NotFoundException, BadRequestException {
        locationService.delete(id);
        return ResponseEntity.ok().build();
    }
}
