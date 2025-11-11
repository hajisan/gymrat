package com.example.gymrat_backend.service;

import com.example.gymrat_backend.dto.TrainingSessionDTO;
import com.example.gymrat_backend.exception.ResourceNotFoundException;
import com.example.gymrat_backend.mapper.TrainingSessionMapper;
import com.example.gymrat_backend.model.TrainingSession;
import com.example.gymrat_backend.repository.TrainingSessionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class TrainingSessionServiceImpl implements TrainingSessionService {
    
    private final TrainingSessionRepository trainingSessionRepository;
    private final TrainingSessionMapper trainingSessionMapper;
    
    public TrainingSessionServiceImpl(TrainingSessionRepository trainingSessionRepository,
                                     TrainingSessionMapper trainingSessionMapper) {
        this.trainingSessionRepository = trainingSessionRepository;
        this.trainingSessionMapper = trainingSessionMapper;
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<TrainingSessionDTO> getAllTrainingSessions() {
        return trainingSessionRepository.findAll().stream()
                .map(trainingSessionMapper::toDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public TrainingSessionDTO getTrainingSessionById(Long id) {
        TrainingSession session = trainingSessionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Træningssession", "id", id));
        return trainingSessionMapper.toDTO(session);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<TrainingSessionDTO> getTrainingSessionsByDateRange(LocalDate startDate, LocalDate endDate) {
        List<TrainingSession> sessions = trainingSessionRepository.findByCreatedAtBetween(startDate, endDate);
        return sessions.stream()
                .map(trainingSessionMapper::toDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public TrainingSessionDTO createTrainingSession(TrainingSessionDTO trainingSessionDTO) {
        TrainingSession session = trainingSessionMapper.toEntity(trainingSessionDTO);
        TrainingSession savedSession = trainingSessionRepository.save(session);
        return trainingSessionMapper.toDTO(savedSession);
    }
    
    @Override
    public TrainingSessionDTO updateTrainingSession(Long id, TrainingSessionDTO trainingSessionDTO) {
        TrainingSession existingSession = trainingSessionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Træningssession", "id", id));
        
        trainingSessionMapper.updateEntityFromDTO(trainingSessionDTO, existingSession);
        TrainingSession updatedSession = trainingSessionRepository.save(existingSession);
        return trainingSessionMapper.toDTO(updatedSession);
    }
    
    @Override
    public void deleteTrainingSession(Long id) {
        if (!trainingSessionRepository.existsById(id)) {
            throw new ResourceNotFoundException("Træningssession", "id", id);
        }
        trainingSessionRepository.deleteById(id);
    }
}
