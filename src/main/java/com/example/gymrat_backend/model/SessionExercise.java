package com.example.gymrat_backend.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

// Den exercise som bliver brugt i en session

@Entity
@Table(name = "session_exercise")

public class SessionerExercise {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int sessionExerciseId;

    @Column(name = "order_number", nullable = false)
    private int orderNumber;

    // Many-to-One relation til TrainingSession og ExerciseSet

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "training_session_id", nullable = false,
    foreignKey = @ForeignKey(name = "fk_se_session"))
    private TrainingSession trainingSession;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "exercise_id", nullable = false,
    foreignKey = @ForeignKey(name = "fk_se_exercise"))
    private Exercise exercise;

    // One-to-Many relation til ExerciseSet

    @OneToMany(mappedBy = "sessionExercise", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("set_number asc, bodySide asc")
    private List<ExerciseSet> sets = new ArrayList<>();

    // Konstruktør - med args og uden

    public SessionerExercise(int sessionExerciseId, int orderNumber, TrainingSession trainingSession, Exercise exercise, List<ExerciseSet> sets) {
        this.sessionExerciseId = sessionExerciseId;
        this.orderNumber = orderNumber;
        this.trainingSession = trainingSession;
        this.exercise = exercise;
        this.sets = sets;
    }

    public SessionerExercise() {}

    // Getter og Setter

    public int getSessionExerciseId() {
        return sessionExerciseId;
    }

    public void setSessionExerciseId(int sessionExerciseId) {
        this.sessionExerciseId = sessionExerciseId;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public TrainingSession getTrainingSession() {
        return trainingSession;
    }

    public void setTrainingSession(TrainingSession trainingSession) {
        this.trainingSession = trainingSession;
    }

    public Exercise getExercise() {
        return exercise;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }

    public List<ExerciseSet> getSets() {
        return sets;
    }

    public void setSets(List<ExerciseSet> sets) {
        this.sets = sets;
    }
}
