package com.example.gymrat_backend.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TrainingSessionDTO {
    
    private Long trainingSessionId;
    
    @NotNull(message = "Dato må ikke være null")
    private LocalDate createdAt;
    
    private String note;
    
    // Liste af performed exercises tilknyttet denne session
    private List<PerformedExerciseDTO> exercises = new ArrayList<>();
    
    // Constructors
    public TrainingSessionDTO() {}
    
    public TrainingSessionDTO(Long trainingSessionId, LocalDate createdAt, String note) {
        this.trainingSessionId = trainingSessionId;
        this.createdAt = createdAt;
        this.note = note;
    }
    
    // Getters and Setters
    public Long getTrainingSessionId() {
        return trainingSessionId;
    }
    
    public void setTrainingSessionId(Long trainingSessionId) {
        this.trainingSessionId = trainingSessionId;
    }
    
    public LocalDate getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }
    
    public String getNote() {
        return note;
    }
    
    public void setNote(String note) {
        this.note = note;
    }
    
    public List<PerformedExerciseDTO> getExercises() {
        return exercises;
    }
    
    public void setExercises(List<PerformedExerciseDTO> exercises) {
        this.exercises = exercises;
    }
}
