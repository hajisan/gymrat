package com.example.gymrat_backend.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

// Training session for en given dato

@Entity
@Table(name = "training_session")

public class TrainingSession {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "training_session_id")
    private Long trainingSessionId;

    @Column(name = "created_at", nullable = false)
    private LocalDate createdAt;

    @Column
    private String note;

    // Relation til SessionExercise = En trænings sessions valgte øvelser
    @OneToMany(
        mappedBy = "session",
        cascade = CascadeType.ALL,
        orphanRemoval = true)
    @OrderBy("orderNumber asc")
    private List<SessionExercise> exercises = new ArrayList<>();

    // Konstruktør - uden args og med

    public TrainingSession() {

    }

    public TrainingSession(LocalDate createdAt, String note) {
        this.createdAt = createdAt;
        this.note = note;
    }

    // Sørger for at createdAt automatisk får dags dato, hvis ingen værdi er sat inden objektet gemmes i DB

    @PrePersist
    void prePersist() {
        if (this.createdAt == null) {
            this.createdAt = LocalDate.now();
        }
    }

    /*  Disse metoder bruges til at vedligeholde den tovejs relation mellem TrainingSession og SessionExercise.
        Når et SessionExercise-objekt tilføjes eller fjernes, opdateres begge sider af relationen,
        så Hibernate altid har et konsistent billede af dataene. */

    public void addExercise(SessionExercise exercise) {
        if (exercise == null) return;
        exercises.add(exercise);
        exercise.setSession(this);
    }

    public void removeExercise(SessionExercise exercise) {
        if (exercise == null) return;
        exercises.remove(exercise);
        exercise.setSession(null);
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

    public List<SessionExercise> getExercises() {
        return exercises;
    }
    public void setExercises(List<SessionExercise> exercises) {
        this.exercises = exercises;
    }

}
