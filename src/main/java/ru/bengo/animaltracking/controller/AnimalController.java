package ru.bengo.animaltracking.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.bengo.animaltracking.dto.AnimalDto;
import ru.bengo.animaltracking.dto.TypeDto;
import ru.bengo.animaltracking.entity.Animal;
import ru.bengo.animaltracking.entity.AnimalType;
import ru.bengo.animaltracking.entity.AnimalVisitedLocation;
import ru.bengo.animaltracking.exception.*;
import ru.bengo.animaltracking.service.AnimalService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@RestController
@RequestMapping("/animals")
@RequiredArgsConstructor
@Slf4j
public class AnimalController {

    private final AnimalService animalService;
    private final ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<AnimalDto> createAnimal(@RequestBody AnimalDto animalDto) throws ConflictException, NotFoundException, BadRequestException {
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToDto(animalService.create(animalDto)));
    }

    @GetMapping("/{animalId}")
    public ResponseEntity<AnimalDto> getAnimal(@PathVariable(value = "animalId") Long id) throws NotFoundException {
        return ResponseEntity.ok(convertToDto(animalService.get(id)));
    }

    @PutMapping("/{animalId}")
    public ResponseEntity<AnimalDto> updateAnimal(@PathVariable("animalId") Long id,
                                               @RequestBody AnimalDto animalDto) throws NotFoundException, BadRequestException {
        return ResponseEntity.ok(convertToDto(animalService.update(id, animalDto)));
    }

    @DeleteMapping("/{animalId}")
    public ResponseEntity<?> deleteAnimal(@PathVariable("animalId") Long id) throws NotFoundException, BadRequestException {
        animalService.delete(id);
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
        return ResponseEntity.ok(animals.stream().map(this::convertToDto).toList());
    }

    @PostMapping("/{animalId}/types/{typeId}")
    public ResponseEntity<AnimalDto> addAnimalTypeToAnimal(@PathVariable("animalId") Long animalId,
                                                        @PathVariable("typeId") Long typeId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToDto(animalService.addAnimalTypeToAnimal(animalId, typeId)));
    }

    @PutMapping("/{animalId}/types")
    public ResponseEntity<AnimalDto> updateAnimalTypeInAnimal(@PathVariable("animalId") Long animalId,
                                                           @RequestBody TypeDto typeDto) {
        return ResponseEntity.ok(convertToDto(animalService.updateAnimalTypesInAnimal(animalId, typeDto)));
    }

    private AnimalDto convertToDto(Animal animal) {
        List<Long> animalTypes = animal.getAnimalTypes().stream().map(AnimalType::getId).toList();
        List<Long> visitedLocations = Optional.ofNullable(animal.getVisitedLocations()).orElse(new ArrayList<>()).stream().map(AnimalVisitedLocation::getId).toList();
        AnimalDto animalDto = modelMapper.map(animal, AnimalDto.class);
        animalDto.setAnimalTypesIds(animalTypes);
        animalDto.setVisitedLocationsIds(visitedLocations);
        return animalDto;
    }

}
