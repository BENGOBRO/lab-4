package ru.bengo.animaltracking.controller;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.bengo.animaltracking.dto.AnimalDto;
import ru.bengo.animaltracking.model.Animal;
import ru.bengo.animaltracking.model.AnimalType;
import ru.bengo.animaltracking.service.AnimalService;
import ru.bengo.animaltracking.service.AnimalTypeService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/animals")
@RequiredArgsConstructor
public class AnimalController {

    private final AnimalService animalService;
    private final AnimalTypeService animalTypeService;

    @GetMapping("/{animalId}")
    public ResponseEntity<?> getAnimalById(@PathVariable(value = "animalId") @NotNull @Min(1)
                                               Integer id) {
        Optional<Animal> foundAnimal = animalService.findById(id);

        if (foundAnimal.isPresent()) {
            return ResponseEntity.ok(foundAnimal.get());
        }

        return ResponseEntity.notFound().build();
    }

    //TODO добавить валидацию параметров запроса
    @GetMapping("/search")
    public ResponseEntity<?> searchAnimals(@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDateTime,
                                           @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDateTime,
                                           @RequestParam(required = false) @Min(1) Integer chipperId,
                                           @RequestParam(required = false) @Min(1) Long chippingLocationId,
                                           @RequestParam(required = false) String lifeStatus,
                                           @RequestParam(required = false) String gender,
                                           @RequestParam(defaultValue = "0") @Min(0) Integer from,
                                           @RequestParam(defaultValue = "10") @Min(1) Integer size) {
        Optional<List<Animal>> foundAnimals = animalService.search(startDateTime, endDateTime,
                chipperId, chippingLocationId, lifeStatus, gender, from, size);

        if (foundAnimals.isPresent()) {
            return ResponseEntity.ok(foundAnimals.get());
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> addAnimal(@RequestBody AnimalDto animalDto) {
        return ResponseEntity.ok(animalService.addAnimal(animalDto));
    }

    @GetMapping("/types/{typeId}")
    public ResponseEntity<?> getAnimalTypeById(@PathVariable @NotNull @Min(1) Long typeId) {
        Optional<AnimalType> foundAnimalType = animalTypeService.getAnimalTypeById(typeId);

        if (foundAnimalType.isPresent()) {
            return ResponseEntity.ok(foundAnimalType.get());
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping("/types")
    public ResponseEntity<?> addAnimalType(@RequestBody AnimalType requestAnimalType) {
        return ResponseEntity.ok(animalTypeService.addAnimalType(requestAnimalType));
    }

    @PutMapping("/types/{typeId}")
    public ResponseEntity<?> changeAnimalType(@PathVariable @NotNull @Min(1) Long typeId) {
        return null;
    }

    @DeleteMapping("/types/{typeId}")
    public ResponseEntity<?> deleteAnimalType(@PathVariable @NotNull @Min(1) Long typeId) {
        return null;
    }
}
