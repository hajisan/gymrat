package com.example.gymrat_backend.model;

import jakarta.persistence.*;

// Det antal set som bliver tilføjet til en Session Exercise - kaldet SessionSet da "Set" er et keyword i MySQL

@Entity
@Table(name = "exercise_set")

public class ExerciseSet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sessionSetId;

    @Enumerated(EnumType.STRING) // gemmes som VARCHAR ('LEFT' / 'RIGHT' / 'BOTH')
    @Column(name = "side_of_body", length = 5, nullable = false)
    private SideOfBody sideOfBody = SideOfBody.BOTH;

    @Column(name = "set_number", nullable = false)
    private Integer setNumber;

    @Column(nullable = false)
    private double weight;

    @Column
    private Integer reps; // reps bruges til øvelser med gentagelser

    @Column
    private Integer durationSeconds; // duractionSeconds bruges til øvelser målt i tid

    @Column
    private String note;

    // Many-to-one relation

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "session_exercise_id", nullable = false,
                foreignKey = @ForeignKey(name = "fk_set_se"))
    private SessionExercise sessionExercise;

    // Konstruktør - uden args og med

    public ExerciseSet() {}

    public ExerciseSet(SideOfBody sideOfBody, Integer setNumber, double weight, Integer reps, Integer durationSeconds, String note) {
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
            throw new IllegalStateException("ExerciseSet must have at least one of 'reps' or 'durationSeconds' set.");
        }
    }

    // Getter og Setter

    public Long getSessionSetId() {
        return sessionSetId;
    }
    public void setSessionSetId(Long sessionSetId) {
        this.sessionSetId = sessionSetId;
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

    public double getWeight() {
        return weight;
    }
    public void setWeight(double weight) {
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

    public SessionExercise getSessionExercise() {
        return sessionExercise;
    }
    public void setSessionExercise(SessionExercise sessionerExercise) {
        this.sessionExercise = sessionerExercise;
    }
}
