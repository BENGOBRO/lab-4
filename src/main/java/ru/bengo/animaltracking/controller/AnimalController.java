package ru.bengo.animaltracking.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.bengo.animaltracking.dto.AnimalDto;
import ru.bengo.animaltracking.exception.AnimalTypeNotFoundException;
import ru.bengo.animaltracking.exception.AnimalTypesHasDuplicatesException;
import ru.bengo.animaltracking.exception.ChipperIdNotFoundException;
import ru.bengo.animaltracking.exception.ChippingLocationIdNotFound;
import ru.bengo.animaltracking.model.Animal;
import ru.bengo.animaltracking.service.AnimalService;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/animals")
@RequiredArgsConstructor
public class AnimalController {

    private final AnimalService animalService;

    @GetMapping("/{animalId}")
    public ResponseEntity<?> getAnimal(@PathVariable(value = "animalId") Long id) {
        Optional<Animal> foundAnimal = animalService.findById(id);

        if (foundAnimal.isPresent()) {
            return ResponseEntity.ok(foundAnimal.get());
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchAnimals(@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDateTime,
                                           @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDateTime,
                                           @RequestParam(required = false) Integer chipperId,
                                           @RequestParam(required = false) Long chippingLocationId,
                                           @RequestParam(required = false) String lifeStatus,
                                           @RequestParam(required = false) String gender,
                                           @RequestParam(defaultValue = "0") Integer from,
                                           @RequestParam(defaultValue = "10") Integer size) {
        return ResponseEntity.ok(animalService.search(startDateTime, endDateTime, chipperId,
                chippingLocationId, lifeStatus, gender, from, size));
    }

    @PostMapping
    public ResponseEntity<?> addAnimal(@RequestBody AnimalDto animalDto) throws AnimalTypesHasDuplicatesException, ChippingLocationIdNotFound, ChipperIdNotFoundException, AnimalTypeNotFoundException {
        return ResponseEntity.ok(animalService.add(animalDto));
    }

    @PutMapping
    public ResponseEntity<?> updateAnimal(@RequestBody AnimalDto animalDto) {
        return ResponseEntity.ok().build();
    }
}
