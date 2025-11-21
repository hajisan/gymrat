package com.example.gymrat_backend.dto.request;

/*
Request DTO til at afslutte en træningssession
Inkluderer session note
 */

import jakarta.validation.constraints.Size;

public class CompleteWorkoutRequest {

    @Size(max = 255, message = "Note må maksimalt være 255 tegn")
    private String note;

    // Konstruktør - uden args og med

    public CompleteWorkoutRequest() {
    }

    public CompleteWorkoutRequest(String note) {
        this.note = note;
    }

    // Getter og setter

    public String getNote() {
        return note;
    }
    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "CompleteWorkoutRequest{" +
                "note='" + note + '\'' +
                '}';
    }
}