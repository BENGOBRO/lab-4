package ru.bengo.animaltracking.api.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.bengo.animaltracking.api.exception.BadRequestException;
import ru.bengo.animaltracking.api.exception.ConflictException;
import ru.bengo.animaltracking.api.exception.ForbiddenException;
import ru.bengo.animaltracking.api.exception.NotFoundException;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> onBadRequestException(BadRequestException ignoredE) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<?> onForbiddenException(ForbiddenException ignoredE) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> onNotFoundException(NotFoundException ignoredE) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<?> onConflictException(ConflictException ignoredE) {
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }
}
