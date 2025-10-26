package com.example.gymrat_backend.repository;

import com.example.gymrat_backend.model.PerformedExercise;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PerformedExerciseRepository extends JpaRepository<PerformedExercise, Long> {

    // CRUD er indbygget i JPA

    // Tilføj evt ekstra metoder hvis nødvendigt

}
