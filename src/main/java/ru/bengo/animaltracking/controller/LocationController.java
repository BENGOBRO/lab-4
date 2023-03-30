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
    public ResponseEntity<?> getLocation(@PathVariable Long pointId) {
        Optional<Location> foundLocation = locationService.getLocation(pointId);

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
    public ResponseEntity<?> changeLocation(@RequestBody Location location,
                                            @PathVariable Long pointId) {
        Location changedLocation = locationService.changeLocation(location, pointId);

        if (changedLocation != null) {
            return ResponseEntity.ok(changedLocation);
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{pointId}")
    public ResponseEntity<?> deleteLocation(@PathVariable Long pointId) {
        locationService.deleteLocation(pointId);
        return ResponseEntity.ok().build();
    }
}
