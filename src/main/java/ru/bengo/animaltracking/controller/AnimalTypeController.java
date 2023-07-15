package ru.bengo.animaltracking.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.bengo.animaltracking.exception.AnimalTypeAlreadyExist;
import ru.bengo.animaltracking.model.AnimalType;
import ru.bengo.animaltracking.service.AnimalTypeService;

import java.util.Optional;

@RestController
@RequestMapping("/animals/types")
@RequiredArgsConstructor
public class AnimalTypeController {

    private final AnimalTypeService animalTypeService;
    private static final Logger log = LoggerFactory.getLogger(AnimalTypeController.class);

    @GetMapping("/{typeId}")
    public ResponseEntity<AnimalType> getAnimalType(@PathVariable("typeId") Long id) {
        Optional<AnimalType> foundAnimalType = animalTypeService.get(id);

        return foundAnimalType.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());

    }

    @PostMapping
    public ResponseEntity<AnimalType> addAnimalType(@RequestBody AnimalType animalType) throws AnimalTypeAlreadyExist {
        log.warn(">>Type controller");
        return ResponseEntity.status(HttpStatus.CREATED).body(animalTypeService.add(animalType));
    }

    @PutMapping("/{typeId}")
    public ResponseEntity<AnimalType> updateAnimalType(@PathVariable("typeId") Long id, @RequestBody AnimalType animalType) throws AnimalTypeAlreadyExist {
        AnimalType updatedAnimalType = animalTypeService.update(id, animalType);

        if (updatedAnimalType == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(updatedAnimalType);
    }

    @DeleteMapping("/{typeId}")
    public ResponseEntity<?> deleteAnimalType(@PathVariable("typeId") Long id) {
        animalTypeService.delete(id);
        return ResponseEntity.ok().build();
    }

}
