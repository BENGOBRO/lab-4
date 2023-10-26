package ru.bengo.animaltracking.api.annotation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ru.bengo.animaltracking.api.annotation.GenderAnnotation;
import ru.bengo.animaltracking.api.model.Gender;

public class GenderValidator implements ConstraintValidator<GenderAnnotation, String> {

    @Override
    public void initialize(GenderAnnotation constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value != null && !value.isEmpty()) {
            return Gender.isGender(value);
        }
        return true;
    }
}
