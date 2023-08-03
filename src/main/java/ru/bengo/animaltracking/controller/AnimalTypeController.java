package ru.bengo.animaltracking.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.bengo.animaltracking.dto.AnimalTypeDto;
import ru.bengo.animaltracking.exception.AnimalTypeAlreadyExist;
import ru.bengo.animaltracking.exception.AnimalTypeNotFoundException;
import ru.bengo.animaltracking.entity.AnimalType;
import ru.bengo.animaltracking.service.AnimalTypeService;

@RestController
@RequestMapping("/animals/types")
@RequiredArgsConstructor
public class AnimalTypeController {

    private final AnimalTypeService animalTypeService;
    private static final Logger log = LoggerFactory.getLogger(AnimalTypeController.class);

    @PostMapping
    public ResponseEntity<AnimalType> createAnimalType(@RequestBody AnimalTypeDto animalTypeDto) throws AnimalTypeAlreadyExist {
        return ResponseEntity.status(HttpStatus.CREATED).body(animalTypeService.create(animalTypeDto));
    }

    @GetMapping("/{typeId}")
    public ResponseEntity<AnimalType> getAnimalType(@PathVariable("typeId") Long id) throws AnimalTypeNotFoundException {
        return ResponseEntity.ok(animalTypeService.get(id));
    }

    @PutMapping("/{typeId}")
    public ResponseEntity<AnimalType> updateAnimalType(@PathVariable("typeId") Long id, @RequestBody AnimalTypeDto animalTypeDto) throws AnimalTypeAlreadyExist, AnimalTypeNotFoundException {
        return ResponseEntity.ok(animalTypeService.update(id, animalTypeDto));
    }

    @DeleteMapping("/{typeId}")
    public ResponseEntity<?> deleteAnimalType(@PathVariable("typeId") Long id) throws AnimalTypeNotFoundException {
        animalTypeService.delete(id);
        return ResponseEntity.ok().build();
    }

}
