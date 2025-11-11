package com.example.gymrat_backend.mapper;

import com.example.gymrat_backend.dto.PerformedSetDTO;
import com.example.gymrat_backend.model.PerformedSet;
import org.springframework.stereotype.Component;

@Component
public class PerformedSetMapper {
    
    /**
     * Konverterer PerformedSet entity til PerformedSetDTO
     */
    public PerformedSetDTO toDTO(PerformedSet set) {
        if (set == null) {
            return null;
        }
        
        PerformedSetDTO dto = new PerformedSetDTO();
        dto.setPerformedSetId(set.getPerformedSetId());
        dto.setSideOfBody(set.getSideOfBody());
        dto.setSetNumber(set.getSetNumber());
        dto.setWeight(set.getWeight());
        dto.setReps(set.getReps());
        dto.setDurationSeconds(set.getDurationSeconds());
        dto.setNote(set.getNote());
        
        if (set.getPerformedExercise() != null) {
            dto.setPerformedExerciseId(set.getPerformedExercise().getPerformedExerciseId());
        }
        
        return dto;
    }
    
    /**
     * Konverterer PerformedSetDTO til PerformedSet entity (uden relations)
     */
    public PerformedSet toEntity(PerformedSetDTO dto) {
        if (dto == null) {
            return null;
        }
        
        PerformedSet set = new PerformedSet();
        set.setPerformedSetId(dto.getPerformedSetId());
        set.setSideOfBody(dto.getSideOfBody());
        set.setSetNumber(dto.getSetNumber());
        set.setWeight(dto.getWeight());
        set.setReps(dto.getReps());
        set.setDurationSeconds(dto.getDurationSeconds());
        set.setNote(dto.getNote());
        
        return set;
    }
    
    /**
     * Opdaterer en eksisterende PerformedSet entity fra en DTO
     */
    public void updateEntityFromDTO(PerformedSetDTO dto, PerformedSet set) {
        if (dto == null || set == null) {
            return;
        }
        
        if (dto.getSideOfBody() != null) {
            set.setSideOfBody(dto.getSideOfBody());
        }
        if (dto.getSetNumber() != null) {
            set.setSetNumber(dto.getSetNumber());
        }
        if (dto.getWeight() != null) {
            set.setWeight(dto.getWeight());
        }
        if (dto.getReps() != null) {
            set.setReps(dto.getReps());
        }
        if (dto.getDurationSeconds() != null) {
            set.setDurationSeconds(dto.getDurationSeconds());
        }
        if (dto.getNote() != null) {
            set.setNote(dto.getNote());
        }
    }
}
