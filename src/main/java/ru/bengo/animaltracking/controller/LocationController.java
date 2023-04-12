package ru.bengo.animaltracking.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.bengo.animaltracking.dto.LocationDto;
import ru.bengo.animaltracking.exception.LocationAlreadyExistException;
import ru.bengo.animaltracking.model.Location;
import ru.bengo.animaltracking.service.LocationService;

import java.util.Optional;

@RestController
@RequestMapping("/locations")
@RequiredArgsConstructor
public class LocationController {

    private final LocationService locationService;

    @GetMapping("/{pointId}")
    public ResponseEntity<?> getLocation(@PathVariable("pointId") Long id) {
        Optional<Location> foundLocation = locationService.getLocation(id);

        if (foundLocation.isPresent()) {
            return ResponseEntity.ok(foundLocation.get());
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> addLocation(@RequestBody LocationDto locationDto) throws LocationAlreadyExistException {
        return ResponseEntity.status(HttpStatus.CREATED).body(locationService.addLocation(locationDto));
    }

    @PutMapping("/{pointId}")
    public ResponseEntity<?> updateLocation(@RequestBody LocationDto locationDto,
                                            @PathVariable("pointId") Long id) throws LocationAlreadyExistException {
        Location changedLocation = locationService.updateLocation(locationDto, id);

        if (changedLocation != null) {
            return ResponseEntity.ok(changedLocation);
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{pointId}")
    public ResponseEntity<?> deleteLocation(@PathVariable("pointId") Long id) {
        Long numOfDeletedEntities = locationService.deleteLocation(id);

        if (numOfDeletedEntities == 0) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().build();
    }
}
