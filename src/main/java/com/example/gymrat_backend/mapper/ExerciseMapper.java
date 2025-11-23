package com.example.gymrat_backend.mapper;

import com.example.gymrat_backend.dto.ExerciseDTO;
import com.example.gymrat_backend.model.Exercise;
import org.springframework.stereotype.Component;

@Component
public class ExerciseMapper {
    
    /**
     * Konverterer Exercise entity til ExerciseDTO
     */
    public ExerciseDTO toDTO(Exercise exercise) {
        if (exercise == null) {
            return null;
        }

        ExerciseDTO dto = new ExerciseDTO();
        dto.setExerciseId(exercise.getExerciseId());
        dto.setName(exercise.getName());
        dto.setTargetMuscleGroup(exercise.getTargetMuscleGroup());
        dto.setEquipment(exercise.getEquipment());
        dto.setExerciseType(exercise.getExerciseType());

        return dto;
    }
    
    /**
     * Konverterer ExerciseDTO til Exercise entity
     */
    public Exercise toEntity(ExerciseDTO dto) {
        if (dto == null) {
            return null;
        }

        Exercise exercise = new Exercise();
        exercise.setExerciseId(dto.getExerciseId());
        exercise.setName(dto.getName());
        exercise.setTargetMuscleGroup(dto.getTargetMuscleGroup());
        exercise.setEquipment(dto.getEquipment());
        exercise.setExerciseType(dto.getExerciseType());

        return exercise;
    }
    
    /**
     * Opdaterer en eksisterende Exercise entity fra en DTO
     */
    public void updateEntityFromDTO(ExerciseDTO dto, Exercise exercise) {
        if (dto == null || exercise == null) {
            return;
        }

        if (dto.getName() != null) {
            exercise.setName(dto.getName());
        }
        if (dto.getTargetMuscleGroup() != null) {
            exercise.setTargetMuscleGroup(dto.getTargetMuscleGroup());
        }
        if (dto.getEquipment() != null) {
            exercise.setEquipment(dto.getEquipment());
        }
        if (dto.getExerciseType() != null) {
            exercise.setExerciseType(dto.getExerciseType());
        }
    }
}
