package com.example.gymrat_backend.dto.request;

/*
Request DTO til at opdatere en eksisterende træningssession.
Alle felter er nullable - kun de felter der er sat vil blive opdateret (partial update)
 */

import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public class UpdateTrainingSessionRequest {

    @PastOrPresent(message = "Training session date cannot be in the future")
    private LocalDate createdAt;

    @Size(max = 255, message = "Note cannot exceed 255 characters")
    private String note;

    // Konstruktør - uden args og med

    public UpdateTrainingSessionRequest() {
    }

    public UpdateTrainingSessionRequest(LocalDate createdAt, String note) {
        this.createdAt = createdAt;
        this.note = note;
    }

    // Getter og setter

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

    // toString

    @Override
    public String toString() {
        return "UpdateTrainingSessionRequest{" +
                "createdAt=" + createdAt +
                ", note='" + note + '\'' +
                '}';
    }
}
