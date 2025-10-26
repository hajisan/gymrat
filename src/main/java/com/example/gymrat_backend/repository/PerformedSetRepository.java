package com.example.gymrat_backend.repository;

import com.example.gymrat_backend.model.PerformedSet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PerformedSetRepository extends JpaRepository<PerformedSet, Long> {

    // CRUD er indbygget i JPA

    // Tilføj evt ekstra metoder hvis nødvendigt

}
