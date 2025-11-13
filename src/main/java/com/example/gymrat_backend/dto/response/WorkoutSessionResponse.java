package com.example.gymrat_backend.dto.response;

/*
Response DTO til aktiv træningssession
Bruges under træning til at vise current state
 */

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class WorkoutSessionResponse {

    private Long trainingSessionId;
    private LocalDateTime startedAt;
    private List<WorkoutExerciseResponse> exercises = new ArrayList<>();

    // Konstruktør - uden args og med

    public WorkoutSessionResponse() {
    }

    public WorkoutSessionResponse(Long trainingSessionId, LocalDateTime startedAt, List<WorkoutExerciseResponse> exercises) {
        this.trainingSessionId = trainingSessionId;
        this.startedAt = startedAt;
        this.exercises = exercises;
    }

    // Getter og setter

    public Long getTrainingSessionId() {
        return trainingSessionId;
    }
    public void setTrainingSessionId(Long trainingSessionId) {
        this.trainingSessionId = trainingSessionId;
    }

    public LocalDateTime getStartedAt() {
        return startedAt;
    }
    public void setStartedAt(LocalDateTime startedAt) {
        this.startedAt = startedAt;
    }

    public List<WorkoutExerciseResponse> getExercises() {
        return exercises;
    }
    public void setExercises(List<WorkoutExerciseResponse> exercises) {
        this.exercises = exercises;
    }

    @Override
    public String toString() {
        return "WorkoutSessionResponse{" +
                "trainingSessionId=" + trainingSessionId +
                ", startedAt=" + startedAt +
                ", exercises=" + exercises +
                '}';
    }
}