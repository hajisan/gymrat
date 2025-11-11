package com.example.gymrat_backend.mapper;

import com.example.gymrat_backend.dto.PerformedExerciseDTO;
import com.example.gymrat_backend.model.PerformedExercise;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class PerformedExerciseMapper {
    
    private final ExerciseMapper exerciseMapper;
    private final PerformedSetMapper performedSetMapper;
    
    public PerformedExerciseMapper(ExerciseMapper exerciseMapper, PerformedSetMapper performedSetMapper) {
        this.exerciseMapper = exerciseMapper;
        this.performedSetMapper = performedSetMapper;
    }
    
    /**
     * Konverterer PerformedExercise entity til PerformedExerciseDTO
     */
    public PerformedExerciseDTO toDTO(PerformedExercise performedExercise) {
        if (performedExercise == null) {
            return null;
        }
        
        PerformedExerciseDTO dto = new PerformedExerciseDTO();
        dto.setPerformedExerciseId(performedExercise.getPerformedExerciseId());
        dto.setOrderNumber(performedExercise.getOrderNumber());
        
        if (performedExercise.getSession() != null) {
            dto.setTrainingSessionId(performedExercise.getSession().getTrainingSessionId());
        }
        
        if (performedExercise.getExercise() != null) {
            dto.setExerciseId(performedExercise.getExercise().getExerciseId());
            dto.setExercise(exerciseMapper.toDTO(performedExercise.getExercise()));
        }
        
        if (performedExercise.getSets() != null && !performedExercise.getSets().isEmpty()) {
            dto.setSets(performedExercise.getSets().stream()
                    .map(performedSetMapper::toDTO)
                    .collect(Collectors.toList()));
        }
        
        return dto;
    }
    
    /**
     * Konverterer PerformedExercise entity til DTO uden at inkludere sets (for at undgå cirkulær reference)
     */
    public PerformedExerciseDTO toDTOWithoutSets(PerformedExercise performedExercise) {
        if (performedExercise == null) {
            return null;
        }
        
        PerformedExerciseDTO dto = new PerformedExerciseDTO();
        dto.setPerformedExerciseId(performedExercise.getPerformedExerciseId());
        dto.setOrderNumber(performedExercise.getOrderNumber());
        
        if (performedExercise.getSession() != null) {
            dto.setTrainingSessionId(performedExercise.getSession().getTrainingSessionId());
        }
        
        if (performedExercise.getExercise() != null) {
            dto.setExerciseId(performedExercise.getExercise().getExerciseId());
            dto.setExercise(exerciseMapper.toDTO(performedExercise.getExercise()));
        }
        
        return dto;
    }
    
    /**
     * Konverterer PerformedExerciseDTO til PerformedExercise entity (uden relations)
     */
    public PerformedExercise toEntity(PerformedExerciseDTO dto) {
        if (dto == null) {
            return null;
        }
        
        PerformedExercise performedExercise = new PerformedExercise();
        performedExercise.setPerformedExerciseId(dto.getPerformedExerciseId());
        performedExercise.setOrderNumber(dto.getOrderNumber());
        
        return performedExercise;
    }
    
    /**
     * Opdaterer en eksisterende PerformedExercise entity fra en DTO
     */
    public void updateEntityFromDTO(PerformedExerciseDTO dto, PerformedExercise performedExercise) {
        if (dto == null || performedExercise == null) {
            return;
        }
        
        if (dto.getOrderNumber() != null) {
            performedExercise.setOrderNumber(dto.getOrderNumber());
        }
    }
}
