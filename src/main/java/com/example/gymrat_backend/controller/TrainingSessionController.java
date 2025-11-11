package com.example.gymrat_backend.controller;

import com.example.gymrat_backend.dto.TrainingSessionDTO;
import com.example.gymrat_backend.service.TrainingSessionService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/training-sessions")
public class TrainingSessionController {
    
    private final TrainingSessionService trainingSessionService;
    
    public TrainingSessionController(TrainingSessionService trainingSessionService) {
        this.trainingSessionService = trainingSessionService;
    }
    
    /**
     * GET /api/training-sessions - Hent alle træningssessioner
     */
    @GetMapping
    public ResponseEntity<List<TrainingSessionDTO>> getAllTrainingSessions() {
        List<TrainingSessionDTO> sessions = trainingSessionService.getAllTrainingSessions();
        return ResponseEntity.ok(sessions);
    }
    
    /**
     * GET /api/training-sessions/{id} - Hent en specifik træningssession
     */
    @GetMapping("/{id}")
    public ResponseEntity<TrainingSessionDTO> getTrainingSessionById(@PathVariable Long id) {
        TrainingSessionDTO session = trainingSessionService.getTrainingSessionById(id);
        return ResponseEntity.ok(session);
    }
    
    /**
     * GET /api/training-sessions/date-range - Hent træningssessioner mellem to datoer
     * Eksempel: /api/training-sessions/date-range?startDate=2025-01-01&endDate=2025-12-31
     */
    @GetMapping("/date-range")
    public ResponseEntity<List<TrainingSessionDTO>> getTrainingSessionsByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<TrainingSessionDTO> sessions = 
            trainingSessionService.getTrainingSessionsByDateRange(startDate, endDate);
        return ResponseEntity.ok(sessions);
    }
    
    /**
     * POST /api/training-sessions - Opret en ny træningssession
     */
    @PostMapping
    public ResponseEntity<TrainingSessionDTO> createTrainingSession(
            @Valid @RequestBody TrainingSessionDTO trainingSessionDTO) {
        TrainingSessionDTO newSession = trainingSessionService.createTrainingSession(trainingSessionDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(newSession);
    }
    
    /**
     * PUT /api/training-sessions/{id} - Opdater en eksisterende træningssession
     */
    @PutMapping("/{id}")
    public ResponseEntity<TrainingSessionDTO> updateTrainingSession(
            @PathVariable Long id,
            @Valid @RequestBody TrainingSessionDTO trainingSessionDTO) {
        TrainingSessionDTO updatedSession = trainingSessionService.updateTrainingSession(id, trainingSessionDTO);
        return ResponseEntity.ok(updatedSession);
    }
    
    /**
     * DELETE /api/training-sessions/{id} - Slet en træningssession
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTrainingSession(@PathVariable Long id) {
        trainingSessionService.deleteTrainingSession(id);
        return ResponseEntity.noContent().build();
    }
}
