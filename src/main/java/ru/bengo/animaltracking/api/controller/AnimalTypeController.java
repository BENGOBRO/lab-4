package ru.bengo.animaltracking.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.bengo.animaltracking.api.dto.AnimalTypeDto;
import ru.bengo.animaltracking.api.dto.mapper.AnimalTypeMapper;
import ru.bengo.animaltracking.service.AnimalTypeService;
import ru.bengo.animaltracking.store.entity.AnimalType;

@RestController
@RequestMapping("/animals/types")
@RequiredArgsConstructor
public class AnimalTypeController {

    private final AnimalTypeService animalTypeService;
    private final AnimalTypeMapper animalTypeMapper;

    @PostMapping
    public ResponseEntity<AnimalTypeDto> createAnimalType(@RequestBody AnimalTypeDto animalTypeDto) {
        AnimalType animalType = animalTypeService.create(animalTypeDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(animalTypeMapper.toDto(animalType));
    }

    @GetMapping("/{typeId}")
    public ResponseEntity<AnimalTypeDto> getAnimalType(@PathVariable("typeId") Long id) {
        AnimalType animalType = animalTypeService.get(id);
        return ResponseEntity.ok(animalTypeMapper.toDto(animalType));
    }

    @PutMapping("/{typeId}")
    public ResponseEntity<AnimalTypeDto> updateAnimalType(@PathVariable("typeId") Long id, @RequestBody AnimalTypeDto animalTypeDto) {
        AnimalType animalType = animalTypeService.update(id, animalTypeDto);
        return ResponseEntity.ok(animalTypeMapper.toDto(animalType));
    }

    @DeleteMapping("/{typeId}")
    public ResponseEntity<?> deleteAnimalType(@PathVariable("typeId") Long id) {
        animalTypeService.delete(id);
        return ResponseEntity.ok().build();
    }
}
