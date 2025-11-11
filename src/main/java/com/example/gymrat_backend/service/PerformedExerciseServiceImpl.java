package com.example.gymrat_backend.service;

import com.example.gymrat_backend.dto.PerformedExerciseDTO;
import com.example.gymrat_backend.exception.ResourceNotFoundException;
import com.example.gymrat_backend.mapper.PerformedExerciseMapper;
import com.example.gymrat_backend.model.Exercise;
import com.example.gymrat_backend.model.PerformedExercise;
import com.example.gymrat_backend.model.TrainingSession;
import com.example.gymrat_backend.repository.ExerciseRepository;
import com.example.gymrat_backend.repository.PerformedExerciseRepository;
import com.example.gymrat_backend.repository.TrainingSessionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PerformedExerciseServiceImpl implements PerformedExerciseService {
    
    private final PerformedExerciseRepository performedExerciseRepository;
    private final TrainingSessionRepository trainingSessionRepository;
    private final ExerciseRepository exerciseRepository;
    private final PerformedExerciseMapper performedExerciseMapper;
    
    public PerformedExerciseServiceImpl(PerformedExerciseRepository performedExerciseRepository,
                                       TrainingSessionRepository trainingSessionRepository,
                                       ExerciseRepository exerciseRepository,
                                       PerformedExerciseMapper performedExerciseMapper) {
        this.performedExerciseRepository = performedExerciseRepository;
        this.trainingSessionRepository = trainingSessionRepository;
        this.exerciseRepository = exerciseRepository;
        this.performedExerciseMapper = performedExerciseMapper;
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<PerformedExerciseDTO> getAllPerformedExercises() {
        return performedExerciseRepository.findAll().stream()
                .map(performedExerciseMapper::toDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public PerformedExerciseDTO getPerformedExerciseById(Long id) {
        PerformedExercise performedExercise = performedExerciseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Udført øvelse", "id", id));
        return performedExerciseMapper.toDTO(performedExercise);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<PerformedExerciseDTO> getPerformedExercisesBySessionId(Long sessionId) {
        List<PerformedExercise> performedExercises = 
            performedExerciseRepository.findBySessionTrainingSessionIdOrderByOrderNumberAsc(sessionId);
        return performedExercises.stream()
                .map(performedExerciseMapper::toDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public PerformedExerciseDTO createPerformedExercise(PerformedExerciseDTO performedExerciseDTO) {
        // Hent training session
        TrainingSession session = trainingSessionRepository.findById(performedExerciseDTO.getTrainingSessionId())
                .orElseThrow(() -> new ResourceNotFoundException("Træningssession", "id", 
                        performedExerciseDTO.getTrainingSessionId()));
        
        // Hent exercise
        Exercise exercise = exerciseRepository.findById(performedExerciseDTO.getExerciseId())
                .orElseThrow(() -> new ResourceNotFoundException("Øvelse", "id", 
                        performedExerciseDTO.getExerciseId()));
        
        // Opret performed exercise
        PerformedExercise performedExercise = new PerformedExercise();
        performedExercise.setOrderNumber(performedExerciseDTO.getOrderNumber());
        performedExercise.setSession(session);
        performedExercise.setExercise(exercise);
        
        PerformedExercise savedPerformedExercise = performedExerciseRepository.save(performedExercise);
        return performedExerciseMapper.toDTO(savedPerformedExercise);
    }
    
    @Override
    public PerformedExerciseDTO updatePerformedExercise(Long id, PerformedExerciseDTO performedExerciseDTO) {
        PerformedExercise existingPerformedExercise = performedExerciseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Udført øvelse", "id", id));
        
        performedExerciseMapper.updateEntityFromDTO(performedExerciseDTO, existingPerformedExercise);
        
        // Opdater session hvis det er ændret
        if (performedExerciseDTO.getTrainingSessionId() != null && 
            !performedExerciseDTO.getTrainingSessionId().equals(
                    existingPerformedExercise.getSession().getTrainingSessionId())) {
            TrainingSession session = trainingSessionRepository.findById(performedExerciseDTO.getTrainingSessionId())
                    .orElseThrow(() -> new ResourceNotFoundException("Træningssession", "id", 
                            performedExerciseDTO.getTrainingSessionId()));
            existingPerformedExercise.setSession(session);
        }
        
        // Opdater exercise hvis det er ændret
        if (performedExerciseDTO.getExerciseId() != null && 
            !performedExerciseDTO.getExerciseId().equals(
                    existingPerformedExercise.getExercise().getExerciseId())) {
            Exercise exercise = exerciseRepository.findById(performedExerciseDTO.getExerciseId())
                    .orElseThrow(() -> new ResourceNotFoundException("Øvelse", "id", 
                            performedExerciseDTO.getExerciseId()));
            existingPerformedExercise.setExercise(exercise);
        }
        
        PerformedExercise updatedPerformedExercise = performedExerciseRepository.save(existingPerformedExercise);
        return performedExerciseMapper.toDTO(updatedPerformedExercise);
    }
    
    @Override
    public void deletePerformedExercise(Long id) {
        if (!performedExerciseRepository.existsById(id)) {
            throw new ResourceNotFoundException("Udført øvelse", "id", id);
        }
        performedExerciseRepository.deleteById(id);
    }
}
