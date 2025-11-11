package com.example.gymrat_backend.dto.response;

/*
Response DTO for en udført øvelse i en træningssession
Nested i TrainingSessionDetailResponse
 */

import java.util.ArrayList;
import java.util.List;

public class PerformedExerciseResponse {

    private Long performedExerciseId;
    private Integer orderNumber;
    private ExerciseInfoResponse exercise;
    private List<PerformedSetResponse> sets = new ArrayList<>();

    // Konstruktør - uden args og med

    public PerformedExerciseResponse() {
    }

    public PerformedExerciseResponse(Long performedExerciseId, Integer orderNumber, ExerciseInfoResponse exercise, List<PerformedSetResponse> sets) {
        this.performedExerciseId = performedExerciseId;
        this.orderNumber = orderNumber;
        this.exercise = exercise;
        this.sets = sets;
    }

    // Getter og setter

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

    public ExerciseInfoResponse getExercise() {
        return exercise;
    }
    public void setExercise(ExerciseInfoResponse exercise) {
        this.exercise = exercise;
    }

    public List<PerformedSetResponse> getSets() {
        return sets;
    }
    public void setSets(List<PerformedSetResponse> sets) {
        this.sets = sets;
    }

    // toString

    @Override
    public String toString() {
        return "PerformedExerciseResponse{" +
                "performedExerciseId=" + performedExerciseId +
                ", orderNumber=" + orderNumber +
                ", exercise=" + exercise +
                ", sets=" + sets +
                '}';
    }
}
