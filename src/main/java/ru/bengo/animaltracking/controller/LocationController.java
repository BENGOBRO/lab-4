package ru.bengo.animaltracking.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.bengo.animaltracking.dto.LocationDto;
import ru.bengo.animaltracking.exception.LocationAlreadyExistException;
import ru.bengo.animaltracking.exception.LocationNotFoundException;
import ru.bengo.animaltracking.entity.Location;
import ru.bengo.animaltracking.service.LocationService;

@RestController
@RequestMapping("/locations")
@RequiredArgsConstructor
public class LocationController {

    private final LocationService locationService;

    @PostMapping
    public ResponseEntity<Location> createLocation(@RequestBody LocationDto locationDto)
            throws LocationAlreadyExistException {
        return ResponseEntity.status(HttpStatus.CREATED).body(locationService.create(locationDto));
    }

    @GetMapping("/{pointId}")
    public ResponseEntity<Location> getLocation(@PathVariable("pointId") Long id) throws LocationNotFoundException {
        return ResponseEntity.ok(locationService.get(id));
    }

    @PutMapping("/{pointId}")
    public ResponseEntity<Location> updateLocation(@RequestBody LocationDto locationDto,
                                            @PathVariable("pointId") Long id)
            throws LocationAlreadyExistException, LocationNotFoundException {
        return ResponseEntity.ok(locationService.update(locationDto, id));
    }

    @DeleteMapping("/{pointId}")
    public ResponseEntity<?> deleteLocation(@PathVariable("pointId") Long id) throws LocationNotFoundException {
        locationService.delete(id);
        return ResponseEntity.ok().build();
    }
}
