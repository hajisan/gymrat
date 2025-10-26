package com.example.gymrat_backend.model;

import jakarta.persistence.*;
import org.hibernate.annotations.Check;

import java.util.ArrayList;
import java.util.List;

// Den øvelser der er blevet udført

@Entity
@Table(name = "performed_exercise", uniqueConstraints = @UniqueConstraint(
            name = "uq_session_order",
            columnNames = {"training_session_id", "order_number"}),
        indexes = {
            @Index(name = "idx_se_session", columnList = "training_session_id"),
            @Index(name = "idx_se_exercise", columnList = "exercise_id")
        }
)
@Check(constraints = "order_number > 0")
public class PerformedExercise {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "performed_exercise_id")
    private Long performedExerciseId;

    @Column(name = "order_number", nullable = false)
    private Integer orderNumber;

    // Many-to-One relation til TrainingSession og Exercise

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "training_session_id", nullable = false,
                foreignKey = @ForeignKey(name = "fk_performed_exercise_session"))
    private TrainingSession session;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "exercise_id", nullable = false,
                foreignKey = @ForeignKey(name = "fk_performed_exercise_exercise"))
    private Exercise exercise;

    // One-to-Many relation til ExerciseSet

    @OneToMany(mappedBy = "performedExercise", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("setNumber asc, sideOfBody asc")
    private List<PerformedSet> sets = new ArrayList<>();

    // Konstruktør - uden args og med

    public PerformedExercise() {}

    public PerformedExercise(Integer orderNumber, TrainingSession session, Exercise exercise) {
        this.orderNumber = orderNumber;
        this.session = session;
        this.exercise = exercise;
    }

    /*  Disse metoder bruges til at vedligeholde den tovejs relation mellem SessionExercise og ExerciseSet.
        Når et ExerciseSet tilføjes eller fjernes, opdateres begge sider af relationen,
        så Hibernate altid har et konsistent billede af dataene. */

    public void addSet(PerformedSet set) {
        if (set == null) return;
        sets.add(set);
        set.setPerformedExercise(this);
    }

    public void removeSet(PerformedSet set) {
        if (set == null) return;
        sets.remove(set);
        set.setPerformedExercise(null);
    }

    // Getter og Setter

    public Long getPerformedExerciseId() {
        return performedExerciseId;
    }
    public void setPerformedExerciseId(Long performedExerciseId) {
        this.performedExerciseId = performedExerciseId;
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

    public List<PerformedSet> getSets() {
        return sets;
    }
    public void setSets(List<PerformedSet> sets) {
        this.sets = sets;
    }
}
