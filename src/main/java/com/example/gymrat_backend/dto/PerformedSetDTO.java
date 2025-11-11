package com.example.gymrat_backend.dto;

import com.example.gymrat_backend.model.SideOfBody;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public class PerformedSetDTO {
    
    private Long performedSetId;
    
    @NotNull(message = "Kropssiden må ikke være null")
    private SideOfBody sideOfBody;
    
    @NotNull(message = "Sætnummer må ikke være null")
    @Positive(message = "Sætnummer skal være positivt")
    private Integer setNumber;
    
    private BigDecimal weight;
    
    private Integer reps;
    
    private Integer durationSeconds;
    
    private String note;
    
    private Long performedExerciseId;
    
    // Constructors
    public PerformedSetDTO() {}
    
    public PerformedSetDTO(Long performedSetId, SideOfBody sideOfBody, Integer setNumber, 
                           BigDecimal weight, Integer reps, Integer durationSeconds, 
                           String note, Long performedExerciseId) {
        this.performedSetId = performedSetId;
        this.sideOfBody = sideOfBody;
        this.setNumber = setNumber;
        this.weight = weight;
        this.reps = reps;
        this.durationSeconds = durationSeconds;
        this.note = note;
        this.performedExerciseId = performedExerciseId;
    }
    
    // Getters and Setters
    public Long getPerformedSetId() {
        return performedSetId;
    }
    
    public void setPerformedSetId(Long performedSetId) {
        this.performedSetId = performedSetId;
    }
    
    public SideOfBody getSideOfBody() {
        return sideOfBody;
    }
    
    public void setSideOfBody(SideOfBody sideOfBody) {
        this.sideOfBody = sideOfBody;
    }
    
    public Integer getSetNumber() {
        return setNumber;
    }
    
    public void setSetNumber(Integer setNumber) {
        this.setNumber = setNumber;
    }
    
    public BigDecimal getWeight() {
        return weight;
    }
    
    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }
    
    public Integer getReps() {
        return reps;
    }
    
    public void setReps(Integer reps) {
        this.reps = reps;
    }
    
    public Integer getDurationSeconds() {
        return durationSeconds;
    }
    
    public void setDurationSeconds(Integer durationSeconds) {
        this.durationSeconds = durationSeconds;
    }
    
    public String getNote() {
        return note;
    }
    
    public void setNote(String note) {
        this.note = note;
    }
    
    public Long getPerformedExerciseId() {
        return performedExerciseId;
    }
    
    public void setPerformedExerciseId(Long performedExerciseId) {
        this.performedExerciseId = performedExerciseId;
    }
}
