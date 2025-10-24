package com.example.gymrat_backend.service;

import com.example.gymrat_backend.model.Exercise;
import com.example.gymrat_backend.model.ExerciseSet;
import com.example.gymrat_backend.repository.ExerciseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExerciseServiceImpl implements ExerciseService {

    private final ExerciseRepository exerciseRepository;

    @Autowired
    public ExerciseServiceImpl(ExerciseRepository exerciseRepository) {
        this.exerciseRepository = exerciseRepository;
    }

    @Override
    public List<Exercise> getAllExercises() {
        return exerciseRepository.findAll();
    }

    @Override
    public Optional<Exercise> getExerciseById(Long id) {
        return exerciseRepository.findById(id);
    }

    @Override
    public Exercise createExercise(Exercise exercise) {
        return exerciseRepository.save(exercise);
    }

    @Override
    public Exercise updateExercise(Long id, Exercise updatedExercise) {
        return exerciseRepository.findById(id)
                .map(existing -> {
                    existing.setName(updatedExercise.getName());
                    existing.setTargetMuscleGroup(updatedExercise.getTargetMuscleGroup());
                    existing.setEquipment(updatedExercise.getEquipment());
                    return exerciseRepository.save(existing);
                })
                .orElseThrow(() -> new IllegalArgumentException("Exercise not found with ID: " + id));
    }

    @Override
    public void deleteExercise(Long id) {
        if (!exerciseRepository.existsById(id)) {
            throw new IllegalArgumentException("Exercise not found with ID: " + id);
        }
        exerciseRepository.deleteById(id);
    }



}
