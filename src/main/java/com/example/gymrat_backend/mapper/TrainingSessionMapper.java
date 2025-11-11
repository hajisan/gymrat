package com.example.gymrat_backend.mapper;

import com.example.gymrat_backend.dto.request.CreateTrainingSessionRequest;
import com.example.gymrat_backend.dto.request.UpdateTrainingSessionRequest;
import com.example.gymrat_backend.dto.response.PerformedExerciseResponse;
import com.example.gymrat_backend.dto.response.TrainingSessionDetailResponse;
import com.example.gymrat_backend.dto.response.TrainingSessionSummaryResponse;
import com.example.gymrat_backend.model.TrainingSession;

import java.util.List;

public class TrainingSessionMapper {

    private TrainingSessionMapper() {
        throw new UnsupportedOperationException("Utility class");
    }

    // 1. TrainingSession -> TrainingSessionSummaryResponse (til liste visning)
    public static TrainingSessionSummaryResponse toSummaryResponse(TrainingSession session) {
        if (session == null) return null;

        TrainingSessionSummaryResponse response = new TrainingSessionSummaryResponse();
        response.setTrainingSessionId(session.getTrainingSessionId());
        response.setCreatedAt(session.getCreatedAt());
        response.setNote(session.getNote());
        response.setExerciseCount(session.getExercises().size()); // Antallet af exercises

        return response;
    }

    // 2. TrainingSession -> TrainingSessionDetailResponse (til detaljeret visning)
    public static TrainingSessionDetailResponse toDetailResponse(TrainingSession session) {
        if (session == null) return null;

        TrainingSessionDetailResponse response = new TrainingSessionDetailResponse();
        response.setTrainingSessionId(session.getTrainingSessionId());
        response.setCreatedAt(session.getCreatedAt());
        response.setNote(session.getNote());

        // Map nested performed exercise entity til DTO
        List<PerformedExerciseResponse> exerciseResponses = session.getExercises().stream()
                .map(PerformedExerciseMapper::toResponse)
                .toList();
        response.setExercises(exerciseResponses);

        return response;
    }

    // 3. CreateTrainingSessionRequest -> TrainingSession (til CREATE)
    public static TrainingSession toEntity(CreateTrainingSessionRequest request) {
        if (request == null) return null;

        TrainingSession session = new TrainingSession();
        session.setCreatedAt(request.getCreatedAt()); // Kan være null - håndteres af @PrePersist
        session.setNote(request.getNote());
        // Setter ikke ID - genereres af database

        return  session;
    }

    // 4. UpdateTrainingSessionRequest -> opdater eksisterende TrainingSession (til UPDATE)
    public static void updateEntity(TrainingSession session, UpdateTrainingSessionRequest request) {
        if (session == null || request == null) return;

        if (request.getCreatedAt() != null) {
            session.setCreatedAt(request.getCreatedAt()); // Kun opdater hvis createdAt bliver sat
        }
        if (request.getNote() != null) {
            session.setNote(request.getNote()); // Kun opdater hvis note blive sat
        }
    }
}
