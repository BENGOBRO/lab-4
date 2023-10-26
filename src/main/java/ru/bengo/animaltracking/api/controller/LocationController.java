package ru.bengo.animaltracking.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.bengo.animaltracking.api.dto.LocationDto;
import ru.bengo.animaltracking.api.dto.mapper.LocationMapper;
import ru.bengo.animaltracking.service.LocationService;
import ru.bengo.animaltracking.store.entity.Location;

@RestController
@RequestMapping("/locations")
@RequiredArgsConstructor
public class LocationController {

    private final LocationService locationService;
    private final LocationMapper locationMapper;

    @PostMapping
    public ResponseEntity<LocationDto> createLocation(@RequestBody LocationDto locationDto) {
        Location location = locationService.create(locationDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(locationMapper.toDto(location));
    }

    @GetMapping("/{pointId}")
    public ResponseEntity<LocationDto> getLocation(@PathVariable("pointId") Long id) {
        Location location = locationService.get(id);
        return ResponseEntity.ok(locationMapper.toDto(location));
    }

    @PutMapping("/{pointId}")
    public ResponseEntity<LocationDto> updateLocation(@RequestBody LocationDto locationDto,
                                                   @PathVariable("pointId") Long id) {
        Location location = locationService.update(locationDto, id);
        return ResponseEntity.ok(locationMapper.toDto(location));
    }

    @DeleteMapping("/{pointId}")
    public ResponseEntity<?> deleteLocation(@PathVariable("pointId") Long id) {
        locationService.delete(id);
        return ResponseEntity.ok().build();
    }
}
