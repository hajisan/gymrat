package com.example.gymrat_backend.repository;

import com.example.gymrat_backend.model.SessionExercise;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionExerciseRepository extends JpaRepository<SessionExercise, Long> {

    // CRUD er indbygget i JPA

    // Tilføj evt ekstra metoder hvis nødvendigt

}
