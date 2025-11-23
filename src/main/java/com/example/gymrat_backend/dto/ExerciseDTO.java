package com.example.gymrat_backend.dto;

import com.example.gymrat_backend.model.ExerciseType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ExerciseDTO {

    private Long exerciseId;

    @NotBlank(message = "Øvelsesnavn må ikke være tomt")
    @Size(max = 100, message = "Øvelsesnavn må maksimalt være 100 tegn")
    private String name;

    private String targetMuscleGroup;

    private String equipment;

    private ExerciseType exerciseType;
    
    // Constructors
    public ExerciseDTO() {}

    public ExerciseDTO(Long exerciseId, String name, String targetMuscleGroup, String equipment) {
        this.exerciseId = exerciseId;
        this.name = name;
        this.targetMuscleGroup = targetMuscleGroup;
        this.equipment = equipment;
        this.exerciseType = ExerciseType.REP_BASED; // Default
    }

    public ExerciseDTO(Long exerciseId, String name, String targetMuscleGroup, String equipment, ExerciseType exerciseType) {
        this.exerciseId = exerciseId;
        this.name = name;
        this.targetMuscleGroup = targetMuscleGroup;
        this.equipment = equipment;
        this.exerciseType = exerciseType;
    }
    
    // Getters and Setters
    public Long getExerciseId() {
        return exerciseId;
    }
    
    public void setExerciseId(Long exerciseId) {
        this.exerciseId = exerciseId;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getTargetMuscleGroup() {
        return targetMuscleGroup;
    }
    
    public void setTargetMuscleGroup(String targetMuscleGroup) {
        this.targetMuscleGroup = targetMuscleGroup;
    }
    
    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }

    public ExerciseType getExerciseType() {
        return exerciseType;
    }

    public void setExerciseType(ExerciseType exerciseType) {
        this.exerciseType = exerciseType;
    }
}
