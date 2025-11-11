package com.example.gymrat_backend.controller;

import com.example.gymrat_backend.dto.request.CreateTrainingSessionRequest;
import com.example.gymrat_backend.dto.request.UpdateTrainingSessionRequest;
import com.example.gymrat_backend.dto.response.TrainingSessionDetailResponse;
import com.example.gymrat_backend.dto.response.TrainingSessionSummaryResponse;
import com.example.gymrat_backend.service.TrainingSessionServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/training-session")
public class TrainingSessionController {

    // TODO: Ændre injection til at bruge TrainingSessionService frem for implementationsklassen.

    private final TrainingSessionServiceImpl service;

    public TrainingSessionController(TrainingSessionServiceImpl service) {
        this.service = service;
    }

    @GetMapping("/list")
    public ResponseEntity<List<TrainingSessionSummaryResponse>> getAllSessions() {
        List<TrainingSessionSummaryResponse> sessions = service.getAllSessions();
        return ResponseEntity.ok(sessions);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TrainingSessionDetailResponse> getSessionById(@PathVariable Long id) {
        TrainingSessionDetailResponse session = service.getSessionById(id);
        return ResponseEntity.ok(session);
    }

    @PostMapping("/create")
    public ResponseEntity<TrainingSessionDetailResponse> createSession(@Valid @RequestBody CreateTrainingSessionRequest request) {
        TrainingSessionDetailResponse created = service.createSession(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TrainingSessionDetailResponse> updateSession(@PathVariable Long id, @Valid @RequestBody UpdateTrainingSessionRequest request) {
        return ResponseEntity.ok(service.updateSession(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSession(@PathVariable Long id) {
        service.deleteSession(id);
        return ResponseEntity.noContent().build();
    }
}
