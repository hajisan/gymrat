package com.example.gymrat_backend.repository;

import com.example.gymrat_backend.model.PerformedExercise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PerformedExerciseRepository extends JpaRepository<PerformedExercise, Long> {

    // Find alle performed exercises for en specifik øvelse
    List<PerformedExercise> findByExerciseExerciseId(Long exerciseId);

}
