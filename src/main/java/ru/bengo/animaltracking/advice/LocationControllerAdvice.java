package ru.bengo.animaltracking.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.bengo.animaltracking.exception.LocationAlreadyExistException;
import ru.bengo.animaltracking.exception.LocationNotFoundException;

@RestControllerAdvice
public class LocationControllerAdvice {

    @ExceptionHandler(LocationAlreadyExistException.class)
    public ResponseEntity<?> onLocationAlreadyExistException(LocationAlreadyExistException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @ExceptionHandler(LocationNotFoundException.class)
    public ResponseEntity<?> onLocationNotFoundException(LocationNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
