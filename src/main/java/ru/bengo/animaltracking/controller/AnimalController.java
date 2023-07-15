package ru.bengo.animaltracking.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.bengo.animaltracking.dto.AnimalDto;
import ru.bengo.animaltracking.dto.TypeDto;
import ru.bengo.animaltracking.exception.*;
import ru.bengo.animaltracking.model.Animal;
import ru.bengo.animaltracking.service.AnimalService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/animals")
@RequiredArgsConstructor
public class AnimalController {

    private final AnimalService animalService;
    private static final Logger log = LoggerFactory.getLogger(AnimalController.class);
    @GetMapping("/{animalId}")
    public ResponseEntity<Animal> getAnimal(@PathVariable(value = "animalId") Long id) {
        Optional<Animal> foundAnimal = animalService.get(id);

        return foundAnimal.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());

    }

    @GetMapping("/search")
    public ResponseEntity<List<Animal>> searchAnimals(@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDateTime,
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
    public ResponseEntity<Animal> addAnimal(@RequestBody AnimalDto animalDto) throws AnimalTypesHasDuplicatesException,
            ChippingLocationIdNotFound, ChipperIdNotFoundException, AnimalTypeNotFoundException {
        return ResponseEntity.status(HttpStatus.CREATED).body(animalService.create(animalDto));
    }

    @PutMapping("/{animalId}")
    public ResponseEntity<Animal> updateAnimal(@PathVariable("animalId") Long id, @RequestBody AnimalDto animalDto) throws AnimalNotFoundException,
            NewChippingLocationIdEqualsFirstVisitedLocationIdException, UpdateDeadToAliveException, ChippingLocationIdNotFound,
            ChipperIdNotFoundException {
        return ResponseEntity.ok(animalService.update(id, animalDto));
    }

    @DeleteMapping("/{animalId}")
    public ResponseEntity<?> deleteAnimal(@PathVariable("animalId") Long id) throws AnimalNotFoundException {
        animalService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{animalId}/types/{typeId}")
    public ResponseEntity<Animal> addAnimalTypeToAnimal(@PathVariable("animalId") Long animalId,
                                                   @PathVariable("typeId") Long typeId) throws AnimalNotFoundException,
            AnimalTypesContainNewAnimalTypeException, AnimalTypeNotFoundException {
        return ResponseEntity.ok(animalService.addAnimalTypeToAnimal(animalId, typeId));
    }

    @PutMapping("/{animalId}/types")
    public ResponseEntity<Animal> updateAnimalTypeInAnimal(@PathVariable("animalId") Long animalId,
                                                      @RequestBody TypeDto typeDto) throws AnimalNotFoundException,
            AnimalTypeAlreadyExist, AnimalTypeNotFoundException, AnimalDoesNotHaveTypeException {
        return ResponseEntity.ok(animalService.updateAnimalTypesInAnimal(animalId, typeDto));
    }
}
