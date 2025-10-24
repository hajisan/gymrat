package com.example.gymrat_backend.repository;

import com.example.gymrat_backend.model.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // Nødvendigt?
public interface ExerciseRepository extends JpaRepository<Exercise, Long> {

    // CRUD er indbygget i JPA

    // Tilføj evt ekstra metoder hvis nødvendigt

}
