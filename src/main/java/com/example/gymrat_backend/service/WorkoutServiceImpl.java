package com.example.gymrat_backend.service;

import com.example.gymrat_backend.dto.request.AddExerciseToWorkoutRequest;
import com.example.gymrat_backend.dto.request.CompleteWorkoutRequest;
import com.example.gymrat_backend.dto.request.LogSetRequest;
import com.example.gymrat_backend.dto.response.WorkoutExerciseResponse;
import com.example.gymrat_backend.dto.response.WorkoutSessionResponse;
import com.example.gymrat_backend.dto.response.WorkoutSetResponse;
import com.example.gymrat_backend.exception.ResourceNotFoundException;
import com.example.gymrat_backend.exception.ValidationException;
import com.example.gymrat_backend.model.*;
import com.example.gymrat_backend.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class WorkoutServiceImpl implements WorkoutService {

    private final TrainingSessionRepository trainingSessionRepository;
    private final ExerciseRepository exerciseRepository;
    private final PerformedExerciseRepository performedExerciseRepository;
    private final PerformedSetRepository performedSetRepository;

    public WorkoutServiceImpl(TrainingSessionRepository trainingSessionRepository,
                              ExerciseRepository exerciseRepository,
                              PerformedExerciseRepository performedExerciseRepository,
                              PerformedSetRepository performedSetRepository) {
        this.trainingSessionRepository = trainingSessionRepository;
        this.exerciseRepository = exerciseRepository;
        this.performedExerciseRepository = performedExerciseRepository;
        this.performedSetRepository = performedSetRepository;
    }

    @Override
    public WorkoutSessionResponse startWorkout() {
        // Opret ny træningssession med dagens dato
        TrainingSession session = new TrainingSession();
        session.setCreatedAt(LocalDate.now());
        session = trainingSessionRepository.save(session);

        // Return response med tom exercise liste
        WorkoutSessionResponse response = new WorkoutSessionResponse();
        response.setTrainingSessionId(session.getTrainingSessionId());
        response.setStartedAt(LocalDateTime.now());
        response.setExercises(new ArrayList<>());

        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public WorkoutSessionResponse getActiveWorkout(Long sessionId) {
        TrainingSession session = trainingSessionRepository.findById(sessionId)
                .orElseThrow(() -> new ResourceNotFoundException("Træningssession", "id", sessionId));

        WorkoutSessionResponse response = new WorkoutSessionResponse();
        response.setTrainingSessionId(session.getTrainingSessionId());
        response.setStartedAt(session.getCreatedAt().atStartOfDay());

        // Map performed exercises til response
        List<WorkoutExerciseResponse> exerciseResponses = session.getExercises().stream()
                .map(this::mapToWorkoutExerciseResponse)
                .collect(Collectors.toList());

        response.setExercises(exerciseResponses);

        return response;
    }

    @Override
    public WorkoutExerciseResponse addExerciseToWorkout(Long sessionId, AddExerciseToWorkoutRequest request) {
        // Hent session
        TrainingSession session = trainingSessionRepository.findById(sessionId)
                .orElseThrow(() -> new ResourceNotFoundException("Træningssession", "id", sessionId));

        // Hent exercise
        Exercise exercise = exerciseRepository.findById(request.getExerciseId())
                .orElseThrow(() -> new ResourceNotFoundException("Øvelse", "id", request.getExerciseId()));

        // Beregn næste order number
        int nextOrder = session.getExercises().size() + 1;

        // Opret performed exercise
        PerformedExercise performedExercise = new PerformedExercise();
        performedExercise.setOrderNumber(nextOrder);
        performedExercise.setSession(session);
        performedExercise.setExercise(exercise);

        performedExercise = performedExerciseRepository.save(performedExercise);

        // Return response med last performed data
        return mapToWorkoutExerciseResponse(performedExercise);
    }

    @Override
    public void removeExerciseFromWorkout(Long sessionId, Long performedExerciseId) {
        // Verificer at performed exercise tilhører denne session
        PerformedExercise performedExercise = performedExerciseRepository.findById(performedExerciseId)
                .orElseThrow(() -> new ResourceNotFoundException("Udført øvelse", "id", performedExerciseId));

        if (!performedExercise.getSession().getTrainingSessionId().equals(sessionId)) {
            throw new ValidationException("Øvelsen tilhører ikke denne træningssession");
        }

        performedExerciseRepository.delete(performedExercise);
    }

    @Override
    public WorkoutSetResponse logSet(Long sessionId, LogSetRequest request) {
        // Hent performed exercise
        PerformedExercise performedExercise = performedExerciseRepository.findById(request.getPerformedExerciseId())
                .orElseThrow(() -> new ResourceNotFoundException("Udført øvelse", "id", request.getPerformedExerciseId()));

        // Verificer session
        if (!performedExercise.getSession().getTrainingSessionId().equals(sessionId)) {
            throw new ValidationException("Øvelsen tilhører ikke denne træningssession");
        }

        // Valider at mindst reps eller duration er sat
        if (request.getReps() == null && request.getDurationSeconds() == null) {
            throw new ValidationException("Enten reps eller duration skal være angivet");
        }

        // Check om set allerede eksisterer (update) eller skal oprettes (create)
        PerformedSet performedSet = performedExercise.getSets().stream()
                .filter(s -> s.getSetNumber().equals(request.getSetNumber()))
                .findFirst()
                .orElse(null);

        if (performedSet == null) {
            // Opret nyt set
            performedSet = new PerformedSet();
            performedSet.setPerformedExercise(performedExercise);
            performedSet.setSetNumber(request.getSetNumber());
        }

        // Opdater værdier
        performedSet.setSideOfBody(request.getSideOfBody());
        performedSet.setWeight(request.getWeight());
        performedSet.setReps(request.getReps());
        performedSet.setDurationSeconds(request.getDurationSeconds());
        performedSet.setNote(request.getNote());

        performedSet = performedSetRepository.save(performedSet);

        // Return response
        return mapToWorkoutSetResponse(performedSet, request.getCompleted());
    }

    @Override
    public void deleteSet(Long sessionId, Long performedSetId) {
        PerformedSet performedSet = performedSetRepository.findById(performedSetId)
                .orElseThrow(() -> new ResourceNotFoundException("Udført sæt", "id", performedSetId));

        // Verificer session
        if (!performedSet.getPerformedExercise().getSession().getTrainingSessionId().equals(sessionId)) {
            throw new ValidationException("Sættet tilhører ikke denne træningssession");
        }

        performedSetRepository.delete(performedSet);
    }

    @Override
    public WorkoutSessionResponse completeWorkout(Long sessionId, CompleteWorkoutRequest request) {
        TrainingSession session = trainingSessionRepository.findById(sessionId)
                .orElseThrow(() -> new ResourceNotFoundException("Træningssession", "id", sessionId));

        // Tilføj note
        session.setNote(request.getNote());
        session = trainingSessionRepository.save(session);

        // Return final session response
        return getActiveWorkout(sessionId);
    }

    @Override
    @Transactional(readOnly = true)
    public WorkoutExerciseResponse.LastPerformedData getLastPerformedData(Long exerciseId) {
        // Hent alle performed exercises for denne exercise (ekskl. dagens session)
        LocalDate today = LocalDate.now();
        List<PerformedExercise> pastPerformances = performedExerciseRepository.findByExerciseExerciseId(exerciseId)
                .stream()
                .filter(pe -> pe.getSession().getCreatedAt().isBefore(today))
                .collect(Collectors.toList());

        if (pastPerformances.isEmpty()) {
            return null; // Ingen tidligere data
        }

        // Hent seneste performance
        PerformedExercise lastPerformance = pastPerformances.stream()
                .max((pe1, pe2) -> pe1.getSession().getCreatedAt().compareTo(pe2.getSession().getCreatedAt()))
                .orElse(null);

        if (lastPerformance == null || lastPerformance.getSets().isEmpty()) {
            return null;
        }

        // Beregn gennemsnit af sets fra sidste performance
        List<PerformedSet> lastSets = lastPerformance.getSets();

        double avgWeight = lastSets.stream()
                .filter(s -> s.getWeight() != null)
                .mapToDouble(s -> s.getWeight().doubleValue())
                .average()
                .orElse(0.0);

        double avgReps = lastSets.stream()
                .filter(s -> s.getReps() != null)
                .mapToInt(PerformedSet::getReps)
                .average()
                .orElse(0.0);

        double avgDuration = lastSets.stream()
                .filter(s -> s.getDurationSeconds() != null)
                .mapToInt(PerformedSet::getDurationSeconds)
                .average()
                .orElse(0.0);

        WorkoutExerciseResponse.LastPerformedData lastData = new WorkoutExerciseResponse.LastPerformedData();
        lastData.setAverageWeight(avgWeight > 0 ? avgWeight : null);
        lastData.setAverageReps(avgReps > 0 ? (int) avgReps : null);
        lastData.setAverageDuration(avgDuration > 0 ? (int) avgDuration : null);

        return lastData;
    }

    // Helper methods

    private WorkoutExerciseResponse mapToWorkoutExerciseResponse(PerformedExercise performedExercise) {
        WorkoutExerciseResponse response = new WorkoutExerciseResponse();
        response.setPerformedExerciseId(performedExercise.getPerformedExerciseId());
        response.setExerciseId(performedExercise.getExercise().getExerciseId());
        response.setExerciseName(performedExercise.getExercise().getName());
        response.setTargetMuscleGroup(performedExercise.getExercise().getTargetMuscleGroup());
        response.setEquipment(performedExercise.getExercise().getEquipment());
        response.setOrderNumber(performedExercise.getOrderNumber());

        // Map sets
        List<WorkoutSetResponse> setResponses = performedExercise.getSets().stream()
                .map(s -> mapToWorkoutSetResponse(s, false))
                .collect(Collectors.toList());
        response.setSets(setResponses);

        // Hent last performed data
        WorkoutExerciseResponse.LastPerformedData lastData =
                getLastPerformedData(performedExercise.getExercise().getExerciseId());
        response.setLastPerformed(lastData);

        return response;
    }

    private WorkoutSetResponse mapToWorkoutSetResponse(PerformedSet set, Boolean completed) {
        WorkoutSetResponse response = new WorkoutSetResponse();
        response.setPerformedSetId(set.getPerformedSetId());
        response.setSetNumber(set.getSetNumber());
        response.setSideOfBody(set.getSideOfBody());
        response.setWeight(set.getWeight());
        response.setReps(set.getReps());
        response.setDurationSeconds(set.getDurationSeconds());
        response.setCompleted(completed != null ? completed : false);
        response.setNote(set.getNote());
        return response;
    }
}