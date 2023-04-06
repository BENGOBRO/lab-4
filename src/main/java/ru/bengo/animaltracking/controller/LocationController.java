package ru.bengo.animaltracking.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
    public ResponseEntity<?> addLocation(@RequestBody Location location) {
        return ResponseEntity.ok(locationService.addLocation(location));
    }

    @PutMapping("/{pointId}")
    public ResponseEntity<?> updateLocation(@RequestBody Location location,
                                            @PathVariable("pointId") Long id) {
        Location changedLocation = locationService.updateLocation(location, id);

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
