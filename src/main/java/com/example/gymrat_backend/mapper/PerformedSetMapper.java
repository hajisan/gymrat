package com.example.gymrat_backend.mapper;

import com.example.gymrat_backend.dto.response.PerformedSetResponse;
import com.example.gymrat_backend.model.PerformedSet;

public class PerformedSetMapper {

    private PerformedSetMapper() {
        throw new UnsupportedOperationException("Utility class");
    }

    // PerformedSet -> PerformedSetResponse
    public static PerformedSetResponse toResponse(PerformedSet set) {
        if (set == null) return null;

        PerformedSetResponse response = new PerformedSetResponse();
        response.setPerformedSetId(set.getPerformedSetId());
        response.setSideOfBody(set.getSideOfBody());
        response.setSetNumber(set.getSetNumber());
        response.setWeight(set.getWeight());
        response.setReps(set.getReps());
        response.setDurationSeconds(set.getDurationSeconds());
        response.setNote(set.getNote());

        return response;
    }
}
