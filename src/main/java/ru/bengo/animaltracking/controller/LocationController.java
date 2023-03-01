package ru.bengo.animaltracking.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
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
    public ResponseEntity<?> getLocation(@PathVariable @NotBlank @Min(1) Long pointId) {
        Optional<Location> foundLocation = locationService.getLocation(pointId);

        if (foundLocation.isPresent()) {
            return ResponseEntity.ok(foundLocation.get());
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> addLocation(@Valid @RequestBody Location location) {
        return ResponseEntity.ok(locationService.addLocation(location));
    }

    @PutMapping("/{pointId}")
    public ResponseEntity<?> changeLocation(@Valid @RequestBody Location location,
                                            @PathVariable @NotBlank @Min(1) Long pointId) {
        Location changedLocation = locationService.changeLocation(location, pointId);

        if (changedLocation != null) {
            return ResponseEntity.ok(changedLocation);
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{pointId}")
    public ResponseEntity<?> deleteLocation(@PathVariable @NotBlank @Min(1) Long pointId) {

        locationService.deleteLocation(pointId);
        return ResponseEntity.ok().build();
    }
}
