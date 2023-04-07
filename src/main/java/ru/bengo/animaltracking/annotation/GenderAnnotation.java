package ru.bengo.animaltracking.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import ru.bengo.animaltracking.annotation.validator.GenderValidator;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ FIELD })
@Retention(RUNTIME)
@Constraint(validatedBy = GenderValidator.class)
@Documented
public @interface GenderAnnotation {

    String message() default "{Gender.invalid}";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };

}
