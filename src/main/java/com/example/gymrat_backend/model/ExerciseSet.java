package com.example.gymrat_backend.model;

import jakarta.persistence.*;

// Det antal set som bliver tilføjet til en Session Exercise - kaldet SessionSet da "Set" er et keyword i MySQL

@Entity
@Table(name = "Exercise_Set")

public class SessionSet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int sessionSetId;

    private Enum side;
    private int set_number;
    private double weight;
    private int reps;
    private String note;

    // Many to one
    private int sessionExerciseId;

}
