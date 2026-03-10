package com.example.gymrat_backend.service;

import com.example.gymrat_backend.dto.response.HomeResponse;
import com.example.gymrat_backend.model.PerformedExercise;
import com.example.gymrat_backend.model.PerformedSet;
import com.example.gymrat_backend.model.TrainingSession;
import com.example.gymrat_backend.repository.PerformedExerciseRepository;
import com.example.gymrat_backend.repository.TrainingSessionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@Service
@Transactional(readOnly = true)
public class HomeServiceImpl implements HomeService {

    private static final Logger logger = LoggerFactory.getLogger(HomeServiceImpl.class);
    private static final int RECENT_TRAININGS_LIMIT = 4;

    private final TrainingSessionRepository trainingSessionRepository;
    private final PerformedExerciseRepository performedExerciseRepository;

    public HomeServiceImpl(TrainingSessionRepository trainingSessionRepository,
                           PerformedExerciseRepository performedExerciseRepository) {
        this.trainingSessionRepository = trainingSessionRepository;
        this.performedExerciseRepository = performedExerciseRepository;
    }

    @Override
    public HomeResponse getHomeSummary() {
        try {
            LocalDate today = LocalDate.now();
            LocalDate yearAgo = today.minusDays(365);

            // Query 1: hent alle completed sessions (365 dage) med exercises i én DB-query
            List<TrainingSession> sessions = trainingSessionRepository
                    .findCompletedByCreatedAtBetweenWithExercises(yearAgo, today);

            // Query 2: fyld Hibernate's cache med sets for alle exercises (undgår N+1 lazy loading)
            if (!sessions.isEmpty()) {
                performedExerciseRepository.fetchSetsForSessions(sessions);
            }

            // Alle tre metoder bruger det allerede-indlæste dataset — ingen yderligere DB-kald
            HomeResponse.WeekStats weekStats = calculateWeekStats(sessions);
            List<HomeResponse.LastTraining> recentTrainings = getRecentTrainings(sessions);
            Map<String, HomeResponse.TrainingDayData> trainingDays = getTrainingDays(sessions);

            return new HomeResponse(weekStats, recentTrainings, trainingDays);
        } catch (Exception e) {
            logger.error("Error getting home summary", e);
            throw e;
        }
    }

    private HomeResponse.WeekStats calculateWeekStats(List<TrainingSession> allSessions) {
        LocalDate today = LocalDate.now();
        LocalDate startOfWeek = today.with(DayOfWeek.MONDAY);
        if (startOfWeek.isAfter(today)) {
            startOfWeek = startOfWeek.minusWeeks(1);
        }

        final LocalDate weekStart = startOfWeek;

        List<TrainingSession> weekSessions = allSessions.stream()
                .filter(s -> !s.getCreatedAt().isBefore(weekStart) && !s.getCreatedAt().isAfter(today))
                .toList();

        int totalTrainings = weekSessions.size();
        int totalSets = 0;
        double totalVolumeKg = 0.0;

        for (TrainingSession session : weekSessions) {
            for (PerformedExercise exercise : session.getExercises()) {
                for (PerformedSet set : exercise.getSets()) {
                    totalSets++;
                    if (set.getWeight() != null && set.getReps() != null) {
                        totalVolumeKg += set.getWeight().doubleValue() * set.getReps();
                    }
                }
            }
        }

        return new HomeResponse.WeekStats(totalTrainings, totalSets, totalVolumeKg);
    }

    private List<HomeResponse.LastTraining> getRecentTrainings(List<TrainingSession> allSessions) {
        return allSessions.stream()
                .limit(RECENT_TRAININGS_LIMIT)
                .map(session -> new HomeResponse.LastTraining(
                        session.getTrainingSessionId(),
                        session.getStartedAt() != null ? session.getStartedAt().toString() : null,
                        session.getCompletedAt() != null ? session.getCompletedAt().toString() : null,
                        session.getNote(),
                        session.getExercises() != null ? session.getExercises().size() : 0
                ))
                .toList();
    }

    private Map<String, HomeResponse.TrainingDayData> getTrainingDays(List<TrainingSession> allSessions) {
        Map<String, HomeResponse.TrainingDayData> trainingDays = new HashMap<>();

        for (TrainingSession session : allSessions) {
            String dateKey = session.getCompletedAt().toLocalDate().toString();
            double dayVolume = 0.0;

            for (PerformedExercise exercise : session.getExercises()) {
                for (PerformedSet set : exercise.getSets()) {
                    if (set.getWeight() != null && set.getReps() != null) {
                        dayVolume += set.getWeight().doubleValue() * set.getReps();
                    }
                }
            }

            if (trainingDays.containsKey(dateKey)) {
                HomeResponse.TrainingDayData existing = trainingDays.get(dateKey);
                existing.setVolumeKg(existing.getVolumeKg() + dayVolume);
            } else {
                trainingDays.put(dateKey, new HomeResponse.TrainingDayData(session.getTrainingSessionId(), dayVolume));
            }
        }

        return trainingDays;
    }
}
