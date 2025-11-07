package com.example.gymrat_backend.mapper;

import com.example.gymrat_backend.dto.response.ExerciseInfoResponse;
import com.example.gymrat_backend.dto.response.PerformedExerciseResponse;
import com.example.gymrat_backend.dto.response.PerformedSetResponse;
import com.example.gymrat_backend.model.PerformedExercise;

import java.util.List;

public class PerformedExerciseMapper {

    private PerformedExerciseMapper() {
        throw new UnsupportedOperationException("Utility class");
    }

    // PerformedExercise -> PerformedExerciseResponse
    public static PerformedExerciseResponse toResponse(PerformedExercise performedExercise) {
        if (performedExercise == null) return null;

        PerformedExerciseResponse response = new PerformedExerciseResponse();
        response.setPerformedExerciseId(performedExercise.getPerformedExerciseId());
        response.setOrderNumber(performedExercise.getOrderNumber());

        // Map nested exercise entity til DTO
        ExerciseInfoResponse exerciseInfo = ExerciseMapper.toInfoResponse(
                performedExercise.getExercise()); // Hent exercise fra performedExercise
        response.setExercise(exerciseInfo);

        // Map nested sets collection til DTO liste
        List<PerformedSetResponse> setResponses = performedExercise.getSets().stream()
                .map(PerformedSetMapper::toResponse)
                .toList();
        response.setSets(setResponses);

        return response;
    }

}
