package com.example.gymrat_backend.service;

import com.example.gymrat_backend.dto.request.CreateTrainingSessionRequest;
import com.example.gymrat_backend.dto.request.UpdateTrainingSessionRequest;
import com.example.gymrat_backend.dto.response.TrainingSessionDetailResponse;
import com.example.gymrat_backend.dto.response.TrainingSessionSummaryResponse;
import com.example.gymrat_backend.mapper.TrainingSessionMapper;
import com.example.gymrat_backend.model.TrainingSession;
import com.example.gymrat_backend.repository.TrainingSessionRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class TrainingSessionServiceImpl implements TrainingSessionService {

    private final TrainingSessionRepository repository;

    public TrainingSessionServiceImpl(TrainingSessionRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<TrainingSessionSummaryResponse> getAllSessions() {
        List<TrainingSession> sessions = repository.findAll();

        return sessions.stream()
               .map(TrainingSessionMapper::toSummaryResponse)
               .toList();
    }

    @Override
    public TrainingSessionDetailResponse getSessionById(Long id) {
        TrainingSession session = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Session not found with ID: " + id));

        return TrainingSessionMapper.toDetailResponse(session);
    }

    @Override
    public TrainingSessionDetailResponse createSession(CreateTrainingSessionRequest request) {
        TrainingSession newSession = TrainingSessionMapper.toEntity(request);
        TrainingSession savedSession = repository.save(newSession);
        return TrainingSessionMapper.toDetailResponse(savedSession);
    }

    @Override
    public TrainingSessionDetailResponse updateSession(Long id, UpdateTrainingSessionRequest request) {
       TrainingSession session = repository.findById(id)
               .orElseThrow(() -> new IllegalArgumentException("Session not found with ID: " + id));

       TrainingSessionMapper.updateEntity(session, request);
       TrainingSession updatedSession = repository.save(session);

       return TrainingSessionMapper.toDetailResponse(updatedSession);
    }

    @Override
    public void deleteSession(Long id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("Session not found with ID: " + id);
        }
        repository.deleteById(id);
    }
}
