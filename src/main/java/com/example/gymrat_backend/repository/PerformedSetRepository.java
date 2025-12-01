package com.example.gymrat_backend.repository;

import com.example.gymrat_backend.model.PerformedExercise;
import com.example.gymrat_backend.model.PerformedSet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PerformedSetRepository extends JpaRepository<PerformedSet, Long> {

    // Find alle performed sets for en performed exercise objekt sorteret efter set_number
    List<PerformedSet> findByPerformedExerciseOrderBySetNumberAsc(PerformedExercise performedExercise);

}
