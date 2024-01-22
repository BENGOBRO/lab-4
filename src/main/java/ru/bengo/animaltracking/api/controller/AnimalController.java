package ru.bengo.animaltracking.api.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.bengo.animaltracking.api.dto.AnimalDto;
import ru.bengo.animaltracking.api.dto.TypeDto;
import ru.bengo.animaltracking.api.dto.mapper.AnimalMapper;
import ru.bengo.animaltracking.api.exception.BadRequestException;
import ru.bengo.animaltracking.api.exception.ConflictException;
import ru.bengo.animaltracking.api.exception.NotFoundException;
import ru.bengo.animaltracking.service.AnimalService;
import ru.bengo.animaltracking.store.entity.Animal;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/animals")
@RequiredArgsConstructor
@Slf4j
public class AnimalController {

    private final AnimalService animalService;
    private final AnimalMapper animalMapper;

    @PostMapping
    public ResponseEntity<AnimalDto> createAnimal(@RequestBody AnimalDto animalDto)
            throws ConflictException, NotFoundException, BadRequestException {
        Animal animal = animalService.create(animalDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(animalMapper.toDto(animal));
    }

    @GetMapping("/{animalId}")
    public ResponseEntity<AnimalDto> getAnimal(@PathVariable Long animalId) {
        Animal animal = animalService.get(animalId);
        return ResponseEntity.ok(animalMapper.toDto(animal));
    }

    @PutMapping("/{animalId}")
    public ResponseEntity<AnimalDto> updateAnimal(@PathVariable Long animalId, @RequestBody AnimalDto animalDto) {
        Animal animal = animalService.update(animalId, animalDto);
        return ResponseEntity.ok(animalMapper.toDto(animal));
    }

    @DeleteMapping("/{animalId}")
    public ResponseEntity<Void> deleteAnimal(@PathVariable Long animalId) {
        animalService.delete(animalId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<AnimalDto>> searchAnimals(@RequestParam(required = false)
                                                      @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDateTime,
                                                      @RequestParam(required = false)
                                                      @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDateTime,
                                                      @RequestParam(required = false) Integer chipperId,
                                                      @RequestParam(required = false) Long chippingLocationId,
                                                      @RequestParam(required = false) String lifeStatus,
                                                      @RequestParam(required = false) String gender,
                                                      @RequestParam(defaultValue = "0") Integer from,
                                                      @RequestParam(defaultValue = "10") Integer size) {
        List<Animal> animals = animalService.search(startDateTime, endDateTime, chipperId,
                chippingLocationId, lifeStatus, gender, from, size);
        return ResponseEntity.ok(animals.stream().map(animalMapper::toDto).toList());
    }

    @PostMapping("/{animalId}/types/{typeId}")
    public ResponseEntity<AnimalDto> addAnimalTypeToAnimal(@PathVariable("animalId") Long animalId,
                                                           @PathVariable("typeId") Long typeId) {
        Animal animal = animalService.addAnimalTypeToAnimal(animalId, typeId);
        return ResponseEntity.status(HttpStatus.CREATED).body(animalMapper.toDto(animal));
    }

    @PutMapping("/{animalId}/types")
    public ResponseEntity<AnimalDto> updateAnimalTypeInAnimal(@PathVariable("animalId") Long animalId,
                                                           @RequestBody TypeDto typeDto) {
        Animal animal = animalService.updateAnimalTypeInAnimal(animalId, typeDto);
        return ResponseEntity.ok(animalMapper.toDto(animal));
    }

    @DeleteMapping("/{animalId}/types/{typeId}")
    public ResponseEntity<AnimalDto> deleteAnimalTypeInAnimal(@PathVariable("animalId") Long animalId,
                                                              @PathVariable("typeId") Long typeId) {
        Animal animal = animalService.deleteAnimalTypeInAnimal(animalId, typeId);
        return ResponseEntity.ok(animalMapper.toDto(animal));
    }
}
