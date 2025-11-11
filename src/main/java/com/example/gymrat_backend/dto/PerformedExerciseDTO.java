package com.example.gymrat_backend.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.ArrayList;
import java.util.List;

public class PerformedExerciseDTO {
    
    private Long performedExerciseId;
    
    @NotNull(message = "Rækkefølge må ikke være null")
    @Positive(message = "Rækkefølge skal være positiv")
    private Integer orderNumber;
    
    @NotNull(message = "Træningssession ID må ikke være null")
    private Long trainingSessionId;
    
    @NotNull(message = "Øvelses ID må ikke være null")
    private Long exerciseId;
    
    // For at vise øvelsesdetaljer i response
    private ExerciseDTO exercise;
    
    // Liste af sets tilknyttet denne performed exercise
    private List<PerformedSetDTO> sets = new ArrayList<>();
    
    // Constructors
    public PerformedExerciseDTO() {}
    
    public PerformedExerciseDTO(Long performedExerciseId, Integer orderNumber, 
                                Long trainingSessionId, Long exerciseId) {
        this.performedExerciseId = performedExerciseId;
        this.orderNumber = orderNumber;
        this.trainingSessionId = trainingSessionId;
        this.exerciseId = exerciseId;
    }
    
    // Getters and Setters
    public Long getPerformedExerciseId() {
        return performedExerciseId;
    }
    
    public void setPerformedExerciseId(Long performedExerciseId) {
        this.performedExerciseId = performedExerciseId;
    }
    
    public Integer getOrderNumber() {
        return orderNumber;
    }
    
    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }
    
    public Long getTrainingSessionId() {
        return trainingSessionId;
    }
    
    public void setTrainingSessionId(Long trainingSessionId) {
        this.trainingSessionId = trainingSessionId;
    }
    
    public Long getExerciseId() {
        return exerciseId;
    }
    
    public void setExerciseId(Long exerciseId) {
        this.exerciseId = exerciseId;
    }
    
    public ExerciseDTO getExercise() {
        return exercise;
    }
    
    public void setExercise(ExerciseDTO exercise) {
        this.exercise = exercise;
    }
    
    public List<PerformedSetDTO> getSets() {
        return sets;
    }
    
    public void setSets(List<PerformedSetDTO> sets) {
        this.sets = sets;
    }
}
