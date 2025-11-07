package com.example.gymrat_backend.service;

import com.example.gymrat_backend.dto.request.CreateTrainingSessionRequest;
import com.example.gymrat_backend.dto.request.UpdateTrainingSessionRequest;
import com.example.gymrat_backend.dto.response.TrainingSessionDetailResponse;
import com.example.gymrat_backend.dto.response.TrainingSessionSummaryResponse;

import java.util.List;

public interface TrainingSessionService {

    List<TrainingSessionSummaryResponse> getAllSessions();

    TrainingSessionDetailResponse getSessionById(Long id);

    TrainingSessionDetailResponse createSession(CreateTrainingSessionRequest request);

    TrainingSessionDetailResponse updateSession(Long id, UpdateTrainingSessionRequest request);

    void deleteSession(Long id);

}
