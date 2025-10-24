package com.example.gymrat_backend.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

// Den exercise som bliver brugt i en session

@Entity
@Table(name = "session_exercise", uniqueConstraints = @UniqueConstraint(
            name = "uq_session_order",
            columnNames = {"training_session_id", "order_number"}),
        indexes = {
            @Index(name = "idx_se_session", columnList = "training_session_id"),
            @Index(name = "idx_se_exercise", columnList = "exercise_id")
        }
)

public class SessionExercise {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sessionExerciseId;

    @Column(name = "order_number", nullable = false)
    private Integer orderNumber;

    // Many-to-One relation til TrainingSession og Exercise

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "training_session_id", nullable = false,
                foreignKey = @ForeignKey(name = "fk_se_session"))
    private TrainingSession session;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "exercise_id", nullable = false,
                foreignKey = @ForeignKey(name = "fk_se_exercise"))
    private Exercise exercise;

    // One-to-Many relation til ExerciseSet

    @OneToMany(mappedBy = "sessionExercise", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("setNumber asc, sideOfBody asc")
    private List<ExerciseSet> sets = new ArrayList<>();

    // Konstruktør - uden args og med

    public SessionExercise() {}

    public SessionExercise( Integer orderNumber, TrainingSession session, Exercise exercise) {
        this.orderNumber = orderNumber;
        this.session = session;
        this.exercise = exercise;
    }

    /*  Disse metoder bruges til at vedligeholde den tovejs relation mellem SessionExercise og ExerciseSet.
        Når et ExerciseSet tilføjes eller fjernes, opdateres begge sider af relationen,
        så Hibernate altid har et konsistent billede af dataene. */

    public void addSet(ExerciseSet set) {
        if (set == null) return;
        sets.add(set);
        set.setSessionExercise(this);
    }

    public void removeSet(ExerciseSet set) {
        if (set == null) return;
        sets.remove(set);
        set.setSessionExercise(null);
    }

    // Getter og Setter

    public Long getSessionExerciseId() {
        return sessionExerciseId;
    }
    public void setSessionExerciseId(Long sessionExerciseId) {
        this.sessionExerciseId = sessionExerciseId;
    }

    public Integer getOrderNumber() {
        return orderNumber;
    }
    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }

    public TrainingSession getSession() {
        return session;
    }
    public void setSession(TrainingSession session) {
        this.session = session;
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
