package ru.bengo.animaltracking.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.bengo.animaltracking.exception.LocationAlreadyExistException;

@RestControllerAdvice
public class LocationControllerAdvice {

    @ExceptionHandler(LocationAlreadyExistException.class)
    public ResponseEntity<?> onLocationAlreadyExistException(LocationAlreadyExistException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

}
