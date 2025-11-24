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
    private LocalDateTime completedAt;
    private String note;
    private List<WorkoutExerciseResponse> exercises = new ArrayList<>();

    // Konstruktør - uden args og med

    public WorkoutSessionResponse() {
    }

    public WorkoutSessionResponse(Long trainingSessionId, LocalDateTime startedAt, LocalDateTime completedAt, String note, List<WorkoutExerciseResponse> exercises) {
        this.trainingSessionId = trainingSessionId;
        this.startedAt = startedAt;
        this.completedAt = completedAt;
        this.note = note;
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

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }
    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }

    public String getNote() {
        return note;
    }
    public void setNote(String note) {
        this.note = note;
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
                ", completedAt=" + completedAt +
                ", note='" + note + '\'' +
                ", exercises=" + exercises +
                '}';
    }
}