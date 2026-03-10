package com.example.gymrat_backend.repository;

import com.example.gymrat_backend.model.PerformedExercise;
import com.example.gymrat_backend.model.TrainingSession;
import org.springframework.data.domain.Pageable;
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

    // Hent seneste performance for en øvelse ekskl. nuværende session — filtrering + sortering + limit i SQL
    @Query("SELECT pe FROM PerformedExercise pe " +
           "LEFT JOIN FETCH pe.sets " +
           "WHERE pe.exercise.exerciseId = :exerciseId " +
           "AND pe.session.trainingSessionId <> :excludeSessionId " +
           "AND pe.session.completedAt IS NOT NULL " +
           "ORDER BY pe.session.trainingSessionId DESC")
    List<PerformedExercise> findLatestForExercise(
            @Param("exerciseId") Long exerciseId,
            @Param("excludeSessionId") Long excludeSessionId,
            Pageable pageable);

}
