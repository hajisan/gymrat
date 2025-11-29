package com.example.gymrat_backend.service;

import com.example.gymrat_backend.dto.ExerciseDTO;
import com.example.gymrat_backend.exception.ResourceNotFoundException;
import com.example.gymrat_backend.mapper.ExerciseMapper;
import com.example.gymrat_backend.model.Exercise;
import com.example.gymrat_backend.repository.ExerciseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ExerciseServiceImpl implements ExerciseService {

    private final ExerciseRepository exerciseRepository;
    private final ExerciseMapper exerciseMapper;

    public ExerciseServiceImpl(ExerciseRepository exerciseRepository, ExerciseMapper exerciseMapper) {
        this.exerciseRepository = exerciseRepository;
        this.exerciseMapper = exerciseMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ExerciseDTO> getAllExercises() {
        return exerciseRepository.findAll().stream()
                .map(exerciseMapper::toDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ExerciseDTO getExerciseById(Long id) {
        Exercise exercise = exerciseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Øvelse", "id", id));
        return exerciseMapper.toDTO(exercise);
    }

    @Override
    public ExerciseDTO createExercise(ExerciseDTO exerciseDTO) {
        Exercise exercise = exerciseMapper.toEntity(exerciseDTO);
        Exercise savedExercise = exerciseRepository.save(exercise);
        return exerciseMapper.toDTO(savedExercise);
    }

    @Override
    public ExerciseDTO updateExercise(Long id, ExerciseDTO exerciseDTO) {
        Exercise existingExercise = exerciseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Øvelse", "id", id));
        
        exerciseMapper.updateEntityFromDTO(exerciseDTO, existingExercise);
        Exercise updatedExercise = exerciseRepository.save(existingExercise);
        return exerciseMapper.toDTO(updatedExercise);
    }

    @Override
    public void deleteExercise(Long id) {
        if (!exerciseRepository.existsById(id)) {
            throw new ResourceNotFoundException("Øvelse", "id", id);
        }
        exerciseRepository.deleteById(id);
    }
}
