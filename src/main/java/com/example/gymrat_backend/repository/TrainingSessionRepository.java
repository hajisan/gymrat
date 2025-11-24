package com.example.gymrat_backend.repository;

import com.example.gymrat_backend.model.TrainingSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TrainingSessionRepository extends JpaRepository<TrainingSession, Long> {

    // CRUD er indbygget i JPA

    // Find training sessions mellem to datoer
    List<TrainingSession> findByCreatedAtBetween(LocalDate startDate, LocalDate endDate);
    
    // Find training sessions for en specifik dato
    List<TrainingSession> findByCreatedAt(LocalDate date);
    
    // Find training sessions efter en dato (sorteret)
    List<TrainingSession> findByCreatedAtAfterOrderByCreatedAtDesc(LocalDate date);

    // Find all training sessions sorteret descending (nyeste først)
    List<TrainingSession> findAllByOrderByTrainingSessionIdDesc();

    // Find all completed training sessions sorteret descending (nyeste først)
    List<TrainingSession> findByCompletedAtIsNotNullOrderByCompletedAtDesc();

}
