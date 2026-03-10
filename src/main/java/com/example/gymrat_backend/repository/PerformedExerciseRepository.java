package com.example.gymrat_backend.repository;

import com.example.gymrat_backend.model.PerformedExercise;
import com.example.gymrat_backend.model.TrainingSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PerformedExerciseRepository extends JpaRepository<PerformedExercise, Long> {

    // Find alle performed exercises for en specifik øvelse
    List<PerformedExercise> findByExerciseExerciseId(Long exerciseId);

    // Hent sets for alle performed exercises på tværs af sessions (fylder Hibernate's cache)
    @Query("SELECT DISTINCT pe FROM PerformedExercise pe LEFT JOIN FETCH pe.sets WHERE pe.session IN :sessions")
    List<PerformedExercise> fetchSetsForSessions(@Param("sessions") List<TrainingSession> sessions);

}
