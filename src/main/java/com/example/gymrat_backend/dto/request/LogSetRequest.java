package com.example.gymrat_backend.dto.request;

/*
Request DTO til at logge/opdatere et set under træning
Bruges til auto-save
 */

import com.example.gymrat_backend.model.SideOfBody;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class LogSetRequest {

    @NotNull(message = "Performed exercise ID er påkrævet")
    private Long performedExerciseId;

    @NotNull(message = "Set nummer er påkrævet")
    private Integer setNumber;

    private SideOfBody sideOfBody = SideOfBody.BOTH;

    private BigDecimal weight;

    private Integer reps;

    private Integer durationSeconds;

    private Boolean completed = false;

    private String note;

    // Konstruktør - uden args og med

    public LogSetRequest() {
    }

    public LogSetRequest(Long performedExerciseId, Integer setNumber, SideOfBody sideOfBody,
                         BigDecimal weight, Integer reps, Integer durationSeconds,
                         Boolean completed, String note) {
        this.performedExerciseId = performedExerciseId;
        this.setNumber = setNumber;
        this.sideOfBody = sideOfBody;
        this.weight = weight;
        this.reps = reps;
        this.durationSeconds = durationSeconds;
        this.completed = completed;
        this.note = note;
    }

    // Getter og setter

    public Long getPerformedExerciseId() {
        return performedExerciseId;
    }
    public void setPerformedExerciseId(Long performedExerciseId) {
        this.performedExerciseId = performedExerciseId;
    }

    public Integer getSetNumber() {
        return setNumber;
    }
    public void setSetNumber(Integer setNumber) {
        this.setNumber = setNumber;
    }

    public SideOfBody getSideOfBody() {
        return sideOfBody;
    }
    public void setSideOfBody(SideOfBody sideOfBody) {
        this.sideOfBody = sideOfBody;
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

    public Boolean getCompleted() {
        return completed;
    }
    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public String getNote() {
        return note;
    }
    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "LogSetRequest{" +
                "performedExerciseId=" + performedExerciseId +
                ", setNumber=" + setNumber +
                ", weight=" + weight +
                ", reps=" + reps +
                ", completed=" + completed +
                '}';
    }
}