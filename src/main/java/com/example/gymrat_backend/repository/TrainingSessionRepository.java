package com.example.gymrat_backend.repository;

import com.example.gymrat_backend.model.TrainingSession;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainingSessionRepository extends JpaRepository<TrainingSession, Long> {

    // CRUD er indbygget i JPA

    // Tilføj evt ekstra metoder hvis nødvendigt

}
