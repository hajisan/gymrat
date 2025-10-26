package com.example.gymrat_backend.model;

import jakarta.persistence.*;
import org.hibernate.annotations.Check;

import java.math.BigDecimal;

// Det antal set som bliver tilføjet til en Session Exercise - kaldet SessionSet da "Set" er et keyword i MySQL

@Entity
@Table(name = "performed_set", uniqueConstraints = @UniqueConstraint(
            name = "uq_performed_set_exercise_setnumber",
            columnNames = {"performed_exercise_id", "set_number"}),
        indexes = {
            @Index(name = "idx_performed_set__exercise", columnList = "performed_exercise_id")
        }
)
@Check(constraints = "(reps IS NOT NULL) OR (duration_seconds IS NOT NULL)")
public class PerformedSet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "performed_set_id")
    private Long performedSetId;

    @Enumerated(EnumType.STRING) // gemmes som VARCHAR ('LEFT' / 'RIGHT' / 'BOTH')
    @Column(name = "side_of_body", length = 5, nullable = false)
    private SideOfBody sideOfBody = SideOfBody.BOTH;

    @Column(name = "set_number", nullable = false)
    private Integer setNumber;

    @Column(name = "weight", precision = 10, scale = 2)
    private BigDecimal weight;

    @Column
    private Integer reps; // reps bruges til øvelser med gentagelser

    @Column(name = "duration_seconds")
    private Integer durationSeconds; // duractionSeconds bruges til øvelser målt i tid

    @Column(name = "note")
    private String note;

    // Many-to-one relation

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "performed_exercise_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_performed_set_performed_exercise"))
    private PerformedExercise performedExercise;

    // Konstruktør - uden args og med

    public PerformedSet() {}

    public PerformedSet(SideOfBody sideOfBody, Integer setNumber, BigDecimal weight, Integer reps, Integer durationSeconds, String note) {
        this.sideOfBody = sideOfBody;
        this.setNumber = setNumber;
        this.weight = weight;
        this.reps = reps;
        this.durationSeconds = durationSeconds;
        this.note = note;
    }


    /*  Valideringsmetode der sikrer, at mindst ét af felterne 'reps' eller 'durationSeconds' er sat
        denne metode bliver automatisk kaldt før objektet gemmes eller opdateres i databasen (@PrePersist og @PreUpdate)
        Formålet er at forhindre at et ExerciseSet gemmes uden nogen form for repetitions- eller tidsangivelse,
        da det ville gøre dataen ufuldstændig og meningsløs i konteksten af træningsøvelser. */
    @PrePersist
    @PreUpdate
    private void validateRepsOrDuration() {
        if (this.reps == null && this.durationSeconds == null) {
            throw new IllegalStateException(
                    "ExerciseSet must have at least one of 'reps' or 'durationSeconds' set.");
        }
    }

    // Getter og Setter

    public Long getPerformedSetId() {
        return performedSetId;
    }
    public void setPerformedSetId(Long performedSetId) {
        this.performedSetId = performedSetId;
    }

    public SideOfBody getSideOfBody() {
        return sideOfBody;
    }
    public void setSideOfBody(SideOfBody sideOfBody) {
        this.sideOfBody = sideOfBody;
    }

    public Integer getSetNumber() {
        return setNumber;
    }
    public void setSetNumber(Integer setNumber) {
        this.setNumber = setNumber;
    }

    public BigDecimal getWeight() {
        return weight;
    }
    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public Integer getReps() {
        return reps;
    }
    public void setReps(Integer reps) {
        this.reps = reps;
    }

    public Integer getDurationSeconds() {
        return durationSeconds;
    }
    public void setDurationSeconds(Integer durationSeconds) {
        this.durationSeconds = durationSeconds;
    }

    public String getNote() {
        return note;
    }
    public void setNote(String note) {
        this.note = note;
    }

    public PerformedExercise getPerformedExercise() {
        return performedExercise;
    }
    public void setPerformedExercise(PerformedExercise performedExercise) {
        this.performedExercise = performedExercise;
    }
}
