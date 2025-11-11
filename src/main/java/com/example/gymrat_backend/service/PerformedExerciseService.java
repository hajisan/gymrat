package com.example.gymrat_backend.service;

import com.example.gymrat_backend.dto.PerformedExerciseDTO;

import java.util.List;

public interface PerformedExerciseService {
    
    List<PerformedExerciseDTO> getAllPerformedExercises();
    
    PerformedExerciseDTO getPerformedExerciseById(Long id);
    
    List<PerformedExerciseDTO> getPerformedExercisesBySessionId(Long sessionId);
    
    PerformedExerciseDTO createPerformedExercise(PerformedExerciseDTO performedExerciseDTO);
    
    PerformedExerciseDTO updatePerformedExercise(Long id, PerformedExerciseDTO performedExerciseDTO);
    
    void deletePerformedExercise(Long id);
}
