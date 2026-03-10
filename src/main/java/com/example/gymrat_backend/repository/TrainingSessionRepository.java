package com.example.gymrat_backend.repository;

import com.example.gymrat_backend.model.TrainingSession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface TrainingSessionRepository extends JpaRepository<TrainingSession, Long> {

    // Find training sessions mellem to datoer
    List<TrainingSession> findByCreatedAtBetween(LocalDate startDate, LocalDate endDate);

    // Find training sessions efter en dato (sorteret)
    List<TrainingSession> findByCreatedAtAfterOrderByCreatedAtDesc(LocalDate date);

    // Find all completed training sessions sorteret descending (nyeste først)
    List<TrainingSession> findByCompletedAtIsNotNullOrderByCompletedAtDesc();

    // Pagineret version af completed sessions
    Page<TrainingSession> findByCompletedAtIsNotNull(Pageable pageable);

    // Hent alle completed sessions med exercises (uden sets - hentes separat for at undgå MultipleBagFetchException)
    @Query("SELECT DISTINCT ts FROM TrainingSession ts " +
           "LEFT JOIN FETCH ts.exercises pe " +
           "LEFT JOIN FETCH pe.exercise " +
           "WHERE ts.completedAt IS NOT NULL " +
           "ORDER BY ts.completedAt DESC")
    List<TrainingSession> findAllCompletedWithExercises();

}
