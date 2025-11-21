package com.example.gymrat_backend.dto.request;

/*
Request DTO til at tilføje en øvelse til aktiv træning
 */

import jakarta.validation.constraints.NotNull;

public class AddExerciseToWorkoutRequest {

    @NotNull(message = "Exercise ID er påkrævet")
    private Long exerciseId;

    // Konstruktør - uden args og med

    public AddExerciseToWorkoutRequest() {
    }

    public AddExerciseToWorkoutRequest(Long exerciseId) {
        this.exerciseId = exerciseId;
    }

    // Getter og setter

    public Long getExerciseId() {
        return exerciseId;
    }
    public void setExerciseId(Long exerciseId) {
        this.exerciseId = exerciseId;
    }

    @Override
    public String toString() {
        return "AddExerciseToWorkoutRequest{" +
                "exerciseId=" + exerciseId +
                '}';
    }
}