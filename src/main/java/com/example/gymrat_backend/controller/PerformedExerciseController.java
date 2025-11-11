package com.example.gymrat_backend.controller;

import com.example.gymrat_backend.dto.PerformedExerciseDTO;
import com.example.gymrat_backend.service.PerformedExerciseService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/performed-exercises")
public class PerformedExerciseController {
    
    private final PerformedExerciseService performedExerciseService;
    
    public PerformedExerciseController(PerformedExerciseService performedExerciseService) {
        this.performedExerciseService = performedExerciseService;
    }
    
    /**
     * GET /api/performed-exercises - Hent alle udførte øvelser
     */
    @GetMapping
    public ResponseEntity<List<PerformedExerciseDTO>> getAllPerformedExercises() {
        List<PerformedExerciseDTO> performedExercises = performedExerciseService.getAllPerformedExercises();
        return ResponseEntity.ok(performedExercises);
    }
    
    /**
     * GET /api/performed-exercises/{id} - Hent en specifik udført øvelse
     */
    @GetMapping("/{id}")
    public ResponseEntity<PerformedExerciseDTO> getPerformedExerciseById(@PathVariable Long id) {
        PerformedExerciseDTO performedExercise = performedExerciseService.getPerformedExerciseById(id);
        return ResponseEntity.ok(performedExercise);
    }
    
    /**
     * GET /api/performed-exercises/session/{sessionId} - Hent alle udførte øvelser for en session
     */
    @GetMapping("/session/{sessionId}")
    public ResponseEntity<List<PerformedExerciseDTO>> getPerformedExercisesBySessionId(
            @PathVariable Long sessionId) {
        List<PerformedExerciseDTO> performedExercises = 
            performedExerciseService.getPerformedExercisesBySessionId(sessionId);
        return ResponseEntity.ok(performedExercises);
    }
    
    /**
     * POST /api/performed-exercises - Opret en ny udført øvelse
     */
    @PostMapping
    public ResponseEntity<PerformedExerciseDTO> createPerformedExercise(
            @Valid @RequestBody PerformedExerciseDTO performedExerciseDTO) {
        PerformedExerciseDTO newPerformedExercise = 
            performedExerciseService.createPerformedExercise(performedExerciseDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(newPerformedExercise);
    }
    
    /**
     * PUT /api/performed-exercises/{id} - Opdater en eksisterende udført øvelse
     */
    @PutMapping("/{id}")
    public ResponseEntity<PerformedExerciseDTO> updatePerformedExercise(
            @PathVariable Long id,
            @Valid @RequestBody PerformedExerciseDTO performedExerciseDTO) {
        PerformedExerciseDTO updatedPerformedExercise = 
            performedExerciseService.updatePerformedExercise(id, performedExerciseDTO);
        return ResponseEntity.ok(updatedPerformedExercise);
    }
    
    /**
     * DELETE /api/performed-exercises/{id} - Slet en udført øvelse
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePerformedExercise(@PathVariable Long id) {
        performedExerciseService.deletePerformedExercise(id);
        return ResponseEntity.noContent().build();
    }
}
