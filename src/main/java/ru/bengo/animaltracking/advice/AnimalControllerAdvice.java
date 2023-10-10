package ru.bengo.animaltracking.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.bengo.animaltracking.exception.*;

@RestControllerAdvice
public class AnimalControllerAdvice {

    @ExceptionHandler(AnimalTypesHasDuplicatesException.class)
    public ResponseEntity<?> onAnimalTypesHasDuplicatesException(AnimalTypesHasDuplicatesException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @ExceptionHandler(AnimalTypeNotFoundException.class)
    public ResponseEntity<?> onAnimalTypeNotFoundException(AnimalTypeNotFoundException e) {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(AnimalNotFoundException.class)
    public ResponseEntity<?> onAnimalNotFoundException(AnimalNotFoundException e) {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(UpdateDeadToAliveException.class)
    public ResponseEntity<?> onUpdateDeadToAliveException(UpdateDeadToAliveException e) {
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(NewChippingLocationIdEqualsFirstVisitedLocationIdException.class)
    public ResponseEntity<?> onNewChippingLocationIdEqualsFirstVisitedLocationIdException(
            NewChippingLocationIdEqualsFirstVisitedLocationIdException e) {
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(AnimalTypesContainNewAnimalTypeException.class)
    public ResponseEntity<?> onAnimalTypesContainNewAnimalTypeException(
            AnimalTypesContainNewAnimalTypeException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @ExceptionHandler(AnimalDoesNotHaveTypeException.class)
    public ResponseEntity<?> onAnimalDoesNotHaveTypeException(AnimalDoesNotHaveTypeException e) {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(AnimalTypeAlreadyExist.class)
    public ResponseEntity<?> onAnimalTypeAlreadyExistException(AnimalTypeAlreadyExist e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }
}
