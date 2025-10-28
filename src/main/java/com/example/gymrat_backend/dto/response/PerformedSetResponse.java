package com.example.gymrat_backend.dto.response;

/*
Response DTO for et performed set
Nested i PerformedExerciseResponse
 */

import com.example.gymrat_backend.model.SideOfBody;

import java.math.BigDecimal;

public class PerformedSetResponse {

    private Long performedSetId;
    private SideOfBody sideOfBody;
    private Integer setNumber;
    private BigDecimal weight;
    private Integer reps;
    private Integer durationSeconds;
    private String note;

    // Konstruktør - uden args og med

    public PerformedSetResponse() {
    }

    public PerformedSetResponse(Long performedSetId, SideOfBody sideOfBody, Integer setNumber,
                                BigDecimal weight, Integer reps, Integer durationSeconds, String note) {
        this.performedSetId = performedSetId;
        this.sideOfBody = sideOfBody;
        this.setNumber = setNumber;
        this.weight = weight;
        this.reps = reps;
        this.durationSeconds = durationSeconds;
        this.note = note;
    }

    // Getter og Setter

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

    // toString

    @Override
    public String toString() {
        return "PerformedSetResponse{" +
                "performedSetId=" + performedSetId +
                ", sideOfBody=" + sideOfBody +
                ", setNumber=" + setNumber +
                ", weight=" + weight +
                ", reps=" + reps +
                ", durationSeconds=" + durationSeconds +
                ", note='" + note + '\'' +
                '}';
    }

}
