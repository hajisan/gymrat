package com.example.gymrat_backend.dto.request;

import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

/*
Request DTO til at oprette en ny træningsession.
createdAt er nullable - hvis ikke sat, bruges dags dato (håndteres i Entity @PrePersist.)
 */

public class CreateTrainingSessionRequest {

    @PastOrPresent(message = "Training session date cannot be in the future")
    private LocalDate createdAt;

    @Size(max = 255, message = "Note cannot exceed 255 characters")
    private String note;

    // Konstruktør - uden args og med

    public CreateTrainingSessionRequest() {
    }

    public CreateTrainingSessionRequest(LocalDate createdAt, String note) {
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
        return "CreateTrainingSessionRequest{" +
                "createdAt=" + createdAt +
                ", note='" + note + '\'' +
                '}';
    }
}
