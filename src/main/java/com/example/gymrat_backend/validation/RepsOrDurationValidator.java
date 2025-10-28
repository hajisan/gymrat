package com.example.gymrat_backend.validation;

/*
Validator implementation for @RepsOrDuration annotation
Sikrer at mindst ét af felterne 'reps' eller 'durationSeconds' er sat
 */

import com.example.gymrat_backend.dto.request.CreatePerformedSetRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class RepsOrDurationValidator implements ConstraintValidator<RepsOrDuration, CreatePerformedSetRequest> {

    @Override
    public void initialize(RepsOrDuration constraintAnnotation) {}

    @Override
    public boolean isValid(CreatePerformedSetRequest request, ConstraintValidatorContext context) {
        if (request == null) {
            return true; // Null-check håndteres af @NotNull på klasse niveau hvis nødvendigt
        }

        // Mindst ét felt skal være sat (ikke null)
        boolean hasReps = request.getReps() != null;
        boolean hasDurations = request.getDurationSeconds() != null;

        return hasReps || hasDurations;
    }
}
