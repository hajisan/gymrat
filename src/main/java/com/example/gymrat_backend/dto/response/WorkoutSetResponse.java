package com.example.gymrat_backend.dto.response;

/*
Response DTO for et set i aktiv træning
 */

import com.example.gymrat_backend.model.SideOfBody;

import java.math.BigDecimal;

public class WorkoutSetResponse {

    private Long performedSetId;
    private Integer setNumber;
    private SideOfBody sideOfBody;
    private BigDecimal weight;
    private Integer reps;
    private Integer durationSeconds;
    private Boolean completed; // Om sættet er checked/done
    private String note;

    // Konstruktør - uden args og med

    public WorkoutSetResponse() {
    }

    public WorkoutSetResponse(Long performedSetId, Integer setNumber, SideOfBody sideOfBody,
                              BigDecimal weight, Integer reps, Integer durationSeconds,
                              Boolean completed, String note) {
        this.performedSetId = performedSetId;
        this.setNumber = setNumber;
        this.sideOfBody = sideOfBody;
        this.weight = weight;
        this.reps = reps;
        this.durationSeconds = durationSeconds;
        this.completed = completed;
        this.note = note;
    }

    // Getter og setter

    public Long getPerformedSetId() {
        return performedSetId;
    }
    public void setPerformedSetId(Long performedSetId) {
        this.performedSetId = performedSetId;
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
        return "WorkoutSetResponse{" +
                "performedSetId=" + performedSetId +
                ", setNumber=" + setNumber +
                ", weight=" + weight +
                ", reps=" + reps +
                ", completed=" + completed +
                '}';
    }
}