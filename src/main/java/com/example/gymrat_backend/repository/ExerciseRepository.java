package com.example.gymrat_backend.repository;

import com.example.gymrat_backend.model.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExerciseRepository extends JpaRepository<Exercise, Long> {

    // CRUD er indbygget i JPA

}
