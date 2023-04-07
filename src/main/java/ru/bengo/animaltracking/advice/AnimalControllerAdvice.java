package ru.bengo.animaltracking.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.bengo.animaltracking.exception.AnimalTypeNotFoundException;
import ru.bengo.animaltracking.exception.AnimalTypesHasDuplicatesException;
import ru.bengo.animaltracking.exception.ChipperIdNotFoundException;
import ru.bengo.animaltracking.exception.ChippingLocationIdNotFound;

@RestControllerAdvice
public class AnimalControllerAdvice {

    @ExceptionHandler(AnimalTypesHasDuplicatesException.class)
    public ResponseEntity<?> onAnimalTypesHasDuplicatesException(AnimalTypesHasDuplicatesException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @ExceptionHandler(AnimalTypeNotFoundException.class)
    public ResponseEntity<?> onAnimalTypeNotFoundException(AnimalTypeNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @ExceptionHandler(ChipperIdNotFoundException.class)
    public ResponseEntity<?> onChipperIdNotFoundException(ChipperIdNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @ExceptionHandler(ChippingLocationIdNotFound.class)
    public ResponseEntity<?> onChippingLocationIdNotFoundException(ChipperIdNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
