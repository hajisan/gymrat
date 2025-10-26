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

    // Konstruktør - uden args og med

    public Exercise() {
    }

    public Exercise(Long exerciseId, String name, String targetMuscleGroup, String equipment) {
        this.exerciseId = exerciseId;
        this.name = name;
        this.targetMuscleGroup = targetMuscleGroup;
        this.equipment = equipment;
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
