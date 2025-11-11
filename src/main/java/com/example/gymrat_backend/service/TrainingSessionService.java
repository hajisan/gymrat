package com.example.gymrat_backend.service;

import com.example.gymrat_backend.dto.TrainingSessionDTO;

import java.time.LocalDate;
import java.util.List;

public interface TrainingSessionService {
    
    List<TrainingSessionDTO> getAllTrainingSessions();
    
    TrainingSessionDTO getTrainingSessionById(Long id);
    
    List<TrainingSessionDTO> getTrainingSessionsByDateRange(LocalDate startDate, LocalDate endDate);
    
    TrainingSessionDTO createTrainingSession(TrainingSessionDTO trainingSessionDTO);
    
    TrainingSessionDTO updateTrainingSession(Long id, TrainingSessionDTO trainingSessionDTO);
    
    void deleteTrainingSession(Long id);
}
