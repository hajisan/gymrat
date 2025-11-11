package com.example.gymrat_backend.validation;

/*
Custom validation annotation, til at sikre mindst ét af felterne 'reps' eller 'durationSeconds' bliver udfyldt
Bruges i CreatePerformedSetRequest
 */

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = RepsOrDurationValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface RepsOrDuration {

    String message() default "Either 'reps' or 'durationSeconds' must be provided";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
