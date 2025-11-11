package com.example.gymrat_backend.repository;

import com.example.gymrat_backend.model.PerformedExercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PerformedExerciseRepository extends JpaRepository<PerformedExercise, Long> {

    // CRUD er indbygget i JPA

    // Find alle performed exercises for en training session sorteret efter order_number
    List<PerformedExercise> findBySessionTrainingSessionIdOrderByOrderNumberAsc(Long trainingSessionId);
    
    // Find alle performed exercises for en specifik øvelse
    List<PerformedExercise> findByExerciseExerciseId(Long exerciseId);

}
