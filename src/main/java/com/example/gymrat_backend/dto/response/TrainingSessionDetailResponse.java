package com.example.gymrat_backend.dto.response;

/*
Response DTO til detaljeret visning af en træningssession
Inkluderer alle nested øvelser og sets for fuld information
 */

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TrainingSessionDetailResponse {

    private Long trainingSessionId;
    private LocalDate createdAt;
    private String note;
    private List<PerformedExerciseResponse> exercises = new ArrayList<>();

    // Konstruktør - uden args og med

    public TrainingSessionDetailResponse() {
    }

    public TrainingSessionDetailResponse(Long trainingSessionId, LocalDate createdAt, String note, List<PerformedExerciseResponse> exercises) {
        this.trainingSessionId = trainingSessionId;
        this.createdAt = createdAt;
        this.note = note;
        this.exercises = exercises;
    }

    // Getter og Setter

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

    public List<PerformedExerciseResponse> getExercises() {
        return exercises;
    }
    public void setExercises(List<PerformedExerciseResponse> exercises) {
        this.exercises = exercises;
    }


    // toString

    @Override
    public String toString() {
        return "TrainingSessionDetailResponse{" +
                "trainingSessionId=" + trainingSessionId +
                ", createdAt=" + createdAt +
                ", note='" + note + '\'' +
                ", exercises=" + exercises +
                '}';
    }
}
