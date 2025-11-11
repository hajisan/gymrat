package com.example.gymrat_backend.mapper;

import com.example.gymrat_backend.dto.TrainingSessionDTO;
import com.example.gymrat_backend.model.TrainingSession;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class TrainingSessionMapper {
    
    private final PerformedExerciseMapper performedExerciseMapper;
    
    public TrainingSessionMapper(PerformedExerciseMapper performedExerciseMapper) {
        this.performedExerciseMapper = performedExerciseMapper;
    }
    
    /**
     * Konverterer TrainingSession entity til TrainingSessionDTO
     */
    public TrainingSessionDTO toDTO(TrainingSession session) {
        if (session == null) {
            return null;
        }
        
        TrainingSessionDTO dto = new TrainingSessionDTO();
        dto.setTrainingSessionId(session.getTrainingSessionId());
        dto.setCreatedAt(session.getCreatedAt());
        dto.setNote(session.getNote());
        
        if (session.getExercises() != null && !session.getExercises().isEmpty()) {
            dto.setExercises(session.getExercises().stream()
                    .map(performedExerciseMapper::toDTO)
                    .collect(Collectors.toList()));
        }
        
        return dto;
    }
    
    /**
     * Konverterer TrainingSession entity til DTO uden at inkludere exercises (for lister)
     */
    public TrainingSessionDTO toDTOWithoutExercises(TrainingSession session) {
        if (session == null) {
            return null;
        }
        
        TrainingSessionDTO dto = new TrainingSessionDTO();
        dto.setTrainingSessionId(session.getTrainingSessionId());
        dto.setCreatedAt(session.getCreatedAt());
        dto.setNote(session.getNote());
        
        return dto;
    }
    
    /**
     * Konverterer TrainingSessionDTO til TrainingSession entity (uden relations)
     */
    public TrainingSession toEntity(TrainingSessionDTO dto) {
        if (dto == null) {
            return null;
        }
        
        TrainingSession session = new TrainingSession();
        session.setTrainingSessionId(dto.getTrainingSessionId());
        session.setCreatedAt(dto.getCreatedAt());
        session.setNote(dto.getNote());
        
        return session;
    }
    
    /**
     * Opdaterer en eksisterende TrainingSession entity fra en DTO
     */
    public void updateEntityFromDTO(TrainingSessionDTO dto, TrainingSession session) {
        if (dto == null || session == null) {
            return;
        }
        
        if (dto.getCreatedAt() != null) {
            session.setCreatedAt(dto.getCreatedAt());
        }
        if (dto.getNote() != null) {
            session.setNote(dto.getNote());
        }
    }
}
