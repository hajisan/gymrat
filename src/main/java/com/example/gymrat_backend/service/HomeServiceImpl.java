package com.example.gymrat_backend.service;

import com.example.gymrat_backend.dto.response.HomeResponse;
import com.example.gymrat_backend.model.PerformedExercise;
import com.example.gymrat_backend.model.PerformedSet;
import com.example.gymrat_backend.model.TrainingSession;
import com.example.gymrat_backend.repository.TrainingSessionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class HomeServiceImpl implements HomeService {

    private final TrainingSessionRepository trainingSessionRepository;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("d. MMM");

    public HomeServiceImpl(TrainingSessionRepository trainingSessionRepository) {
        this.trainingSessionRepository = trainingSessionRepository;
    }

    @Override
    public HomeResponse getHomeSummary() {
        try {
            // Beregn ugestatistik (sidste 7 dage)
            HomeResponse.WeekStats weekStats = calculateWeekStats();

            // Hent seneste 4 træninger
            List<HomeResponse.LastTraining> recentTrainings = getRecentTrainings();

            // Hent træningsdage for kalender (sidste år)
            java.util.Map<String, HomeResponse.TrainingDayData> trainingDays = getTrainingDays();

            HomeResponse response = new HomeResponse(weekStats, recentTrainings, trainingDays);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * Beregner statistik for den aktuelle uge (mandag til søndag)
     * Inkluderer kun COMPLETED workouts
     */
    private HomeResponse.WeekStats calculateWeekStats() {
        LocalDate today = LocalDate.now();

        // Find den seneste mandag (start af ugen)
        LocalDate startOfWeek = today.with(java.time.DayOfWeek.MONDAY);

        // Hvis vi er før mandag (søndag), så er startOfWeek næste mandag - vi vil have forrige mandag
        if (startOfWeek.isAfter(today)) {
            startOfWeek = startOfWeek.minusWeeks(1);
        }

        // Hent alle sessions fra denne uge (mandag til nu)
        List<TrainingSession> sessions = trainingSessionRepository.findByCreatedAtBetween(startOfWeek, today);

        // Filtrér for kun completed sessions
        List<TrainingSession> completedSessions = sessions.stream()
                .filter(session -> session.getCompletedAt() != null)
                .toList();

        int totalTrainings = completedSessions.size();
        int totalSets = 0;
        double totalVolumeKg = 0.0;

        // Gennemgå alle sessions og beregn sets + volumen
        for (TrainingSession session : completedSessions) {
            for (PerformedExercise exercise : session.getExercises()) {
                for (PerformedSet set : exercise.getSets()) {
                    totalSets++;

                    // Beregn volumen: vægt * reps (ignorer duration-baserede øvelser)
                    if (set.getWeight() != null && set.getReps() != null) {
                        double weight = set.getWeight().doubleValue();
                        int reps = set.getReps();
                        totalVolumeKg += (weight * reps);
                    }
                }
            }
        }

        return new HomeResponse.WeekStats(totalTrainings, totalSets, totalVolumeKg);
    }

    /**
     * Henter de seneste 4 træninger med formateret dato og note
     * Returnerer kun COMPLETED workouts (hvor completedAt er sat)
     */
    private List<HomeResponse.LastTraining> getRecentTrainings() {
        // Hent alle sessions sorteret efter dato (nyeste først)
        LocalDate today = LocalDate.now();
        List<TrainingSession> recentSessions = trainingSessionRepository
                .findByCreatedAtAfterOrderByCreatedAtDesc(today.minusMonths(3));

        // Filtrér for kun completed sessions (completedAt er ikke null) og tag de første 4
        List<TrainingSession> completedSessions = recentSessions.stream()
                .filter(session -> session.getCompletedAt() != null)
                .limit(4)
                .toList();

        // Map til LastTraining objekter
        return completedSessions.stream()
                .map(session -> {
                    Long trainingSessionId = session.getTrainingSessionId();
                    String startedAt = session.getStartedAt() != null ? session.getStartedAt().toString() : null;
                    String completedAt = session.getCompletedAt() != null ? session.getCompletedAt().toString() : null;
                    String note = session.getNote();
                    Integer exerciseCount = session.getExercises() != null ? session.getExercises().size() : 0;

                    return new HomeResponse.LastTraining(trainingSessionId, startedAt, completedAt, note, exerciseCount);
                })
                .toList();
    }

    /**
     * Formaterer dato til brugervenlig visning
     * - "I dag" hvis det er i dag
     * - "I går" hvis det er i går
     * - "8. nov." for ældre datoer
     */
    private String formatTrainingDate(LocalDate trainingDate) {
        LocalDate today = LocalDate.now();

        if (trainingDate.equals(today)) {
            return "I dag";
        } else if (trainingDate.equals(today.minusDays(1))) {
            return "I går";
        } else {
            return trainingDate.format(DATE_FORMATTER);
        }
    }

    /**
     * Henter træningsdage med volumen og session ID (sidste år / 365 dage)
     * Returnerer map med dato -> TrainingDayData (sessionId + volumen)
     * Returnerer kun COMPLETED workouts
     */
    private java.util.Map<String, HomeResponse.TrainingDayData> getTrainingDays() {
        LocalDate today = LocalDate.now();
        LocalDate monthAgo = today.minusDays(365);

        System.out.println("=== getTrainingDays() called ===");
        System.out.println("Fetching sessions between " + monthAgo + " and " + today);

        // Hent alle sessions fra sidste år
        List<TrainingSession> sessions = trainingSessionRepository
                .findByCreatedAtBetween(monthAgo, today);

        System.out.println("Found " + sessions.size() + " total sessions");

        // Beregn volumen per dag og gem session ID
        java.util.Map<String, HomeResponse.TrainingDayData> trainingDays = new java.util.HashMap<>();

        for (TrainingSession session : sessions) {
            System.out.println("Processing session ID: " + session.getTrainingSessionId()
                + ", createdAt: " + session.getCreatedAt()
                + ", completedAt: " + session.getCompletedAt());
            // Spring over sessions der ikke er completed
            if (session.getCompletedAt() == null) {
                System.out.println("Skipping incomplete session: " + session.getTrainingSessionId());
                continue;
            }

            // Brug completedAt dato for kalenderen (ikke createdAt)
            String dateKey = session.getCompletedAt().toLocalDate().toString();
            System.out.println("Adding session " + session.getTrainingSessionId() + " to calendar on date: " + dateKey);
            double dayVolume = 0.0;

            // Beregn volumen for denne session
            for (PerformedExercise exercise : session.getExercises()) {
                for (PerformedSet set : exercise.getSets()) {
                    if (set.getWeight() != null && set.getReps() != null) {
                        dayVolume += set.getWeight().doubleValue() * set.getReps();
                    }
                }
            }

            // Hvis der allerede er en session denne dag, summer volumen
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