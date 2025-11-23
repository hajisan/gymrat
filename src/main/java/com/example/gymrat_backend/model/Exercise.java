package com.example.gymrat_backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

// Exercise der indeholder navn, muskel gruppe og udstyr

@Entity
@Table(name = "exercise",
        uniqueConstraints = @UniqueConstraint(
                name = "uq_exercise_name",
                columnNames = "name")
        )

public class Exercise {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "exercise_id")
    private Long exerciseId;

    @NotBlank(message = "Exercise name cannot be blank")
    @Size(max = 100)
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "target_muscle_group")
    private String targetMuscleGroup;

    private String equipment;

    @Enumerated(EnumType.STRING)
    @Column(name = "exercise_type", nullable = false, length = 20)
    private ExerciseType exerciseType = ExerciseType.REP_BASED; // Default til reps

    // Konstruktør - uden args og med

    public Exercise() {
    }

    public Exercise(Long exerciseId, String name, String targetMuscleGroup, String equipment) {
        this.exerciseId = exerciseId;
        this.name = name;
        this.targetMuscleGroup = targetMuscleGroup;
        this.equipment = equipment;
        this.exerciseType = ExerciseType.REP_BASED; // Default
    }

    public Exercise(Long exerciseId, String name, String targetMuscleGroup, String equipment, ExerciseType exerciseType) {
        this.exerciseId = exerciseId;
        this.name = name;
        this.targetMuscleGroup = targetMuscleGroup;
        this.equipment = equipment;
        this.exerciseType = exerciseType;
    }

    // Getter og Setter

    public Long getExerciseId() {
        return exerciseId;
    }
    public void setExerciseId(Long exerciseId) {
        this.exerciseId = exerciseId;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getTargetMuscleGroup() {
        return targetMuscleGroup;
    }
    public void setTargetMuscleGroup(String targetMuscleGroup) {
        this.targetMuscleGroup = targetMuscleGroup;
    }

    public String getEquipment() {
        return equipment;
    }
    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }

    public ExerciseType getExerciseType() {
        return exerciseType;
    }
    public void setExerciseType(ExerciseType exerciseType) {
        this.exerciseType = exerciseType;
    }

    // toString
    @Override
    public String toString() {
        return "Exercise{" +
                "name='" + name + '\'' +
                ", targetMuscleGroup='" + targetMuscleGroup + '\'' +
                ", equipment='" + equipment + '\'' +
                '}';
    }

}
