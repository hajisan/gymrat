package com.example.gymrat_backend.repository;

import com.example.gymrat_backend.model.PerformedSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PerformedSetRepository extends JpaRepository<PerformedSet, Long> {

    // CRUD er indbygget i JPA

    // Find alle performed sets for en performed exercise sorteret efter set_number
    List<PerformedSet> findByPerformedExercisePerformedExerciseIdOrderBySetNumberAsc(Long performedExerciseId);

}
