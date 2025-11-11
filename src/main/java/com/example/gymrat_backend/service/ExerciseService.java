package com.example.gymrat_backend.service;

import com.example.gymrat_backend.dto.ExerciseDTO;

import java.util.List;

public interface ExerciseService {

    List<ExerciseDTO> getAllExercises();

    ExerciseDTO getExerciseById(Long id);

    ExerciseDTO createExercise(ExerciseDTO exerciseDTO);

    ExerciseDTO updateExercise(Long id, ExerciseDTO exerciseDTO);

    void deleteExercise(Long id);

}
