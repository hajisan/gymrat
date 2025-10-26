package com.example.gymrat_backend.repository;

import com.example.gymrat_backend.model.ExerciseSet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExerciseSetRepository extends JpaRepository<ExerciseSet, Long> {

    // CRUD er indbygget i JPA

    // Tilføj evt ekstra metoder hvis nødvendigt

}
