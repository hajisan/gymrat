package com.example.gymrat_backend.controller;

import com.example.gymrat_backend.dto.request.AddExerciseToWorkoutRequest;
import com.example.gymrat_backend.dto.request.CompleteWorkoutRequest;
import com.example.gymrat_backend.dto.request.LogSetRequest;
import com.example.gymrat_backend.dto.response.TrainingSessionSummaryResponse;
import com.example.gymrat_backend.dto.response.WorkoutExerciseResponse;
import com.example.gymrat_backend.dto.response.WorkoutSessionResponse;
import com.example.gymrat_backend.dto.response.WorkoutSetResponse;
import com.example.gymrat_backend.service.WorkoutService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/workout")
public class WorkoutController {

    private final WorkoutService workoutService;

    public WorkoutController(WorkoutService workoutService) {
        this.workoutService = workoutService;
    }

    /**
     * POST /api/workout/start - Start ny træning
     */
    @PostMapping("/start")
    public ResponseEntity<WorkoutSessionResponse> startWorkout() {
        WorkoutSessionResponse response = workoutService.startWorkout();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * GET /api/workout/{sessionId} - Hent aktiv træning
     */
    @GetMapping("/{sessionId}")
    public ResponseEntity<WorkoutSessionResponse> getActiveWorkout(@PathVariable Long sessionId) {
        WorkoutSessionResponse response = workoutService.getActiveWorkout(sessionId);
        return ResponseEntity.ok(response);
    }

    /**
     * POST /api/workout/{sessionId}/exercises - Tilføj øvelse til træning
     */
    @PostMapping("/{sessionId}/exercises")
    public ResponseEntity<WorkoutExerciseResponse> addExercise(
            @PathVariable Long sessionId,
            @Valid @RequestBody AddExerciseToWorkoutRequest request) {
        WorkoutExerciseResponse response = workoutService.addExerciseToWorkout(sessionId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * DELETE /api/workout/{sessionId}/exercises/{performedExerciseId} - Fjern øvelse
     */
    @DeleteMapping("/{sessionId}/exercises/{performedExerciseId}")
    public ResponseEntity<Void> removeExercise(
            @PathVariable Long sessionId,
            @PathVariable Long performedExerciseId) {
        workoutService.removeExerciseFromWorkout(sessionId, performedExerciseId);
        return ResponseEntity.noContent().build();
    }

    /**
     * POST /api/workout/{sessionId}/sets - Log/opdater et set (auto-save)
     */
    @PostMapping("/{sessionId}/sets")
    public ResponseEntity<WorkoutSetResponse> logSet(
            @PathVariable Long sessionId,
            @Valid @RequestBody LogSetRequest request) {
        WorkoutSetResponse response = workoutService.logSet(sessionId, request);
        return ResponseEntity.ok(response);
    }

    /**
     * DELETE /api/workout/{sessionId}/sets/{performedSetId} - Slet et set
     */
    @DeleteMapping("/{sessionId}/sets/{performedSetId}")
    public ResponseEntity<Void> deleteSet(
            @PathVariable Long sessionId,
            @PathVariable Long performedSetId) {
        workoutService.deleteSet(sessionId, performedSetId);
        return ResponseEntity.noContent().build();
    }

    /**
     * POST /api/workout/{sessionId}/complete - Afslut træning
     */
    @PostMapping("/{sessionId}/complete")
    public ResponseEntity<WorkoutSessionResponse> completeWorkout(
            @PathVariable Long sessionId,
            @Valid @RequestBody CompleteWorkoutRequest request) {
        WorkoutSessionResponse response = workoutService.completeWorkout(sessionId, request);
        return ResponseEntity.ok(response);
    }

    /**
     * GET /api/workout/exercises/{exerciseId}/last-performed - Hent sidste performance data
     */
    @GetMapping("/exercises/{exerciseId}/last-performed")
    public ResponseEntity<WorkoutExerciseResponse.LastPerformedData> getLastPerformed(
            @PathVariable Long exerciseId) {
        WorkoutExerciseResponse.LastPerformedData data = workoutService.getLastPerformedData(exerciseId);
        return ResponseEntity.ok(data);
    }

    /**
     * GET /api/workout/history - Hent alle træningssessioner (bagudkompatibel, uden pagination)
     */
    @GetMapping("/history")
    public ResponseEntity<List<TrainingSessionSummaryResponse>> getWorkoutHistory() {
        List<TrainingSessionSummaryResponse> workouts = workoutService.getAllWorkouts();
        return ResponseEntity.ok(workouts);
    }

    /**
     * GET /api/workout/history/paged - Hent træningssessioner med pagination
     * @param page sidetal (0-indexed, default 0)
     * @param size antal per side (default 10, max 50)
     */
    @GetMapping("/history/paged")
    public ResponseEntity<Page<TrainingSessionSummaryResponse>> getWorkoutHistoryPaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        // Begræns size til max 50 for at undgå store queries
        int limitedSize = Math.min(size, 50);
        Pageable pageable = PageRequest.of(page, limitedSize, Sort.by(Sort.Direction.DESC, "completedAt"));
        Page<TrainingSessionSummaryResponse> workouts = workoutService.getWorkoutsPaginated(pageable);
        return ResponseEntity.ok(workouts);
    }

    /**
     * DELETE /api/workout/{sessionId} - Slet en træningssession
     */
    @DeleteMapping("/{sessionId}")
    public ResponseEntity<Void> deleteWorkout(@PathVariable Long sessionId) {
        workoutService.deleteWorkout(sessionId);
        return ResponseEntity.noContent().build();
    }
}