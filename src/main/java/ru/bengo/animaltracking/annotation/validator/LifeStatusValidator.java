package ru.bengo.animaltracking.annotation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ru.bengo.animaltracking.annotation.LifeStatusAnnotation;
import ru.bengo.animaltracking.model.LifeStatus;

public class LifeStatusValidator implements ConstraintValidator<LifeStatusAnnotation, String> {

    @Override
    public void initialize(LifeStatusAnnotation constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value != null && !value.isEmpty()) {
            return LifeStatus.isLifeStatus(value);
        }
        return true;
    }
}
