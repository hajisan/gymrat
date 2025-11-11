package com.example.gymrat_backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ExerciseDTO {
    
    private Long exerciseId;
    
    @NotBlank(message = "Øvelsesnavn må ikke være tomt")
    @Size(max = 100, message = "Øvelsesnavn må maksimalt være 100 tegn")
    private String name;
    
    private String targetMuscleGroup;
    
    private String equipment;
    
    // Constructors
    public ExerciseDTO() {}
    
    public ExerciseDTO(Long exerciseId, String name, String targetMuscleGroup, String equipment) {
        this.exerciseId = exerciseId;
        this.name = name;
        this.targetMuscleGroup = targetMuscleGroup;
        this.equipment = equipment;
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
}
