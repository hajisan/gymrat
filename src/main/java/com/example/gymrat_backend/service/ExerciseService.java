package com.example.gymrat_backend.service;

import com.example.gymrat_backend.model.Exercise;
import com.example.gymrat_backend.repository.ExerciseRepository;

import java.util.List;
import java.util.Optional;

public interface ExerciseService {

    List<Exercise> getAllExercises();

    Optional<Exercise> getExerciseById(Long id);

    Exercise createExercise(Exercise exercise);

    Exercise updateExercise(Long id, Exercise exercise);

    void deleteExercise(Long id);

}
