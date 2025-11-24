package com.example.gymrat_backend.service;

import com.example.gymrat_backend.dto.request.AddExerciseToWorkoutRequest;
import com.example.gymrat_backend.dto.request.CompleteWorkoutRequest;
import com.example.gymrat_backend.dto.request.LogSetRequest;
import com.example.gymrat_backend.dto.response.TrainingSessionSummaryResponse;
import com.example.gymrat_backend.dto.response.WorkoutExerciseResponse;
import com.example.gymrat_backend.dto.response.WorkoutSessionResponse;
import com.example.gymrat_backend.dto.response.WorkoutSetResponse;

import java.util.List;

public interface WorkoutService {

    /**
     * Start ny træningssession
     */
    WorkoutSessionResponse startWorkout();

    /**
     * Hent aktiv træningssession
     */
    WorkoutSessionResponse getActiveWorkout(Long sessionId);

    /**
     * Tilføj øvelse til aktiv træning
     */
    WorkoutExerciseResponse addExerciseToWorkout(Long sessionId, AddExerciseToWorkoutRequest request);

    /**
     * Fjern øvelse fra aktiv træning
     */
    void removeExerciseFromWorkout(Long sessionId, Long performedExerciseId);

    /**
     * Log/opdater et set (auto-save)
     */
    WorkoutSetResponse logSet(Long sessionId, LogSetRequest request);

    /**
     * Slet et set
     */
    void deleteSet(Long sessionId, Long performedSetId);

    /**
     * Afslut træning (med note)
     */
    WorkoutSessionResponse completeWorkout(Long sessionId, CompleteWorkoutRequest request);

    /**
     * Hent "last performed" data for en øvelse
     */
    WorkoutExerciseResponse.LastPerformedData getLastPerformedData(Long exerciseId);

    /**
     * Hent alle træningssessioner (historik)
     */
    List<TrainingSessionSummaryResponse> getAllWorkouts();

    /**
     * Slet en træningssession
     */
    void deleteWorkout(Long sessionId);
}