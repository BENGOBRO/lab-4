package ru.bengo.animaltracking.api.advice;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ValidationControllerAdvice {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> onConstraintViolationException() {
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> onMethodArgumentNotValidException() {
        return ResponseEntity.badRequest().build();
    }
}
