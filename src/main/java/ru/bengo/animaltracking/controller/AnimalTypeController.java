package ru.bengo.animaltracking.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.bengo.animaltracking.dto.AnimalTypeDto;
import ru.bengo.animaltracking.entity.AnimalType;
import ru.bengo.animaltracking.exception.BadRequestException;
import ru.bengo.animaltracking.exception.ConflictException;
import ru.bengo.animaltracking.exception.NotFoundException;
import ru.bengo.animaltracking.service.AnimalTypeService;

@RestController
@RequestMapping("/animals/types")
@RequiredArgsConstructor
public class AnimalTypeController {

    private final AnimalTypeService animalTypeService;

    @PostMapping
    public ResponseEntity<AnimalType> createAnimalType(@RequestBody AnimalTypeDto animalTypeDto) throws ConflictException {
        return ResponseEntity.status(HttpStatus.CREATED).body(animalTypeService.create(animalTypeDto));
    }

    @GetMapping("/{typeId}")
    public ResponseEntity<AnimalType> getAnimalType(@PathVariable("typeId") Long id) throws NotFoundException {
        return ResponseEntity.ok(animalTypeService.get(id));
    }

    @PutMapping("/{typeId}")
    public ResponseEntity<AnimalType> updateAnimalType(@PathVariable("typeId") Long id, @RequestBody AnimalTypeDto animalTypeDto) throws ConflictException, NotFoundException {
        return ResponseEntity.ok(animalTypeService.update(id, animalTypeDto));
    }

    @DeleteMapping("/{typeId}")
    public ResponseEntity<?> deleteAnimalType(@PathVariable("typeId") Long id) throws NotFoundException, BadRequestException {
        animalTypeService.delete(id);
        return ResponseEntity.ok().build();
    }

}
