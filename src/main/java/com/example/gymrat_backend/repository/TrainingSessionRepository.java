package com.example.gymrat_backend.repository;

import com.example.gymrat_backend.model.TrainingSession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TrainingSessionRepository extends JpaRepository<TrainingSession, Long> {

    // Find training sessions mellem to datoer
    List<TrainingSession> findByCreatedAtBetween(LocalDate startDate, LocalDate endDate);
    
    // Find training sessions efter en dato (sorteret)
    List<TrainingSession> findByCreatedAtAfterOrderByCreatedAtDesc(LocalDate date);

    // Find all completed training sessions sorteret descending (nyeste først)
    List<TrainingSession> findByCompletedAtIsNotNullOrderByCompletedAtDesc();

}
