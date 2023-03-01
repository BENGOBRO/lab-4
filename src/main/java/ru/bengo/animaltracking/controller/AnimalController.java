package ru.bengo.animaltracking.controller;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.bengo.animaltracking.dto.AnimalDto;
import ru.bengo.animaltracking.model.Animal;
import ru.bengo.animaltracking.service.AnimalService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/animals")
@RequiredArgsConstructor
public class AnimalController {

    private final AnimalService animalService;

    @GetMapping("/{animalId}")
    public ResponseEntity<?> getAnimalById(@PathVariable(value = "animalId") @NotBlank @Min(1)
                                               Integer id) {
        Optional<Animal> foundAnimal = animalService.findById(id);

        if (foundAnimal.isPresent()) {
            return ResponseEntity.ok(foundAnimal.get());
        }

        return ResponseEntity.notFound().build();
    }

    //TODO добавить валидацию параметров запроса
    @GetMapping("/search")
    public ResponseEntity<?> searchAnimals(@RequestParam(required = false) String startDateTime,
                                           @RequestParam(required = false) String endDateTime,
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

//    @GetMapping("/types/{typeId}")
//    public ResponseEntity<?> getTypeOfAnimalById(@PathVariable Long typeId) {
//
//    }
}
