package com.example.gymrat_backend.dto.response;

/*
Response DTO til at liste-visning af træningsessioner
Indeholder kun grundlæggende information uden nested data for performance
 */

import java.time.LocalDate;

public class TrainingSessionSummaryResponse {

    private Long trainingSessionId;
    private LocalDate createdAt;
    private String note;
    private Integer exerciseCount;
    // Tilføj evt andre brugbare information, hvis nødvendigt

    // Konstruktør - uden args og med

    public TrainingSessionSummaryResponse() {
    }

    public TrainingSessionSummaryResponse(Long trainingSessionId, LocalDate createdAt, String note, Integer exerciseCount) {
        this.trainingSessionId = trainingSessionId;
        this.createdAt = createdAt;
        this.note = note;
        this.exerciseCount = exerciseCount;
    }

    // Getter og setter

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

    public Integer getExerciseCount() {
        return exerciseCount;
    }
    public void setExerciseCount(Integer exerciseCount) {
        this.exerciseCount = exerciseCount;
    }

    // toString

    @Override
    public String toString() {
        return "TrainingSessionSummaryResponse{" +
                "trainingSessionId=" + trainingSessionId +
                ", createdAt=" + createdAt +
                ", note='" + note + '\'' +
                ", exerciseCount=" + exerciseCount +
                '}';
    }
}
