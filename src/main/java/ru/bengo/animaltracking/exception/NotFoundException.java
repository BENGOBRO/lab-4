package ru.bengo.animaltracking.exception;

import org.aspectj.weaver.ast.Not;

public class NotFoundException extends Exception{
    public NotFoundException(String message) {
        super(message);
    }
}
