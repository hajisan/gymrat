package com.example.gymrat_backend.controller;

import com.example.gymrat_backend.model.Exercise;
import com.example.gymrat_backend.service.ExerciseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/exercise")
public class ExerciseController {

    private final ExerciseService exerciseService;

    public ExerciseController(ExerciseService exerciseService) {
        this.exerciseService = exerciseService;
    }

    // Get mapping for at hente alle øvelser
    @GetMapping("/list")
    public List<Exercise> getAllExercises() {
        return exerciseService.getAllExercises();
    }

    // Get mapping for at hente en specifik øvelse ud fra ID
    @GetMapping("/{id}")
    public Optional<Exercise> getExercise(@PathVariable Long id) {
        return exerciseService.getExerciseById(id);
    }

    // Post mapping til at oprette en øvelse
    @PostMapping("/create")
    public ResponseEntity<Exercise> createExercise(@RequestBody Exercise exercise) {
        Exercise newExercise = exerciseService.createExercise(exercise);
        return ResponseEntity.ok(newExercise);
    }

    // Patch mapping til at opdatere en specifik øvelse ud fra ID
    @PatchMapping("/{id}")
    public ResponseEntity<Exercise> updateExercise(@PathVariable("id") Long id, @RequestBody Exercise patch) {
        Exercise updatedExercise = exerciseService.updateExercise(id, patch);
        return ResponseEntity.ok(updatedExercise);
    }

    // Delet mapping til at slette en øvelse på ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExercise(@PathVariable("id") Long id) {
        exerciseService.deleteExercise(id);
        return ResponseEntity.noContent().build();
    }

}
