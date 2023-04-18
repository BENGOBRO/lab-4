package ru.bengo.animaltracking.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.bengo.animaltracking.exception.AnimalTypeAlreadyExist;
import ru.bengo.animaltracking.model.AnimalType;
import ru.bengo.animaltracking.service.AnimalTypeService;

import java.util.Optional;

@RestController
@RequestMapping("/animals/type")
@RequiredArgsConstructor
public class AnimalTypeController {

    private final AnimalTypeService animalTypeService;

    @GetMapping("/{typeId}")
    public ResponseEntity<AnimalType> getAnimalType(@PathVariable("typeId") Long id) {
        Optional<AnimalType> foundAnimalType = animalTypeService.get(id);

        return foundAnimalType.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());

    }

    @PostMapping
    public ResponseEntity<AnimalType> addAnimalType(@RequestBody AnimalType animalType) throws AnimalTypeAlreadyExist {
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
        Long numOfDeletedEntities = animalTypeService.delete(id);

        if (numOfDeletedEntities == 0) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().build();
    }

}
