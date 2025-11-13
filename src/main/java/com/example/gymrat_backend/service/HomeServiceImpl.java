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
        // Beregn ugestatistik (sidste 7 dage)
        HomeResponse.WeekStats weekStats = calculateWeekStats();

        // Hent seneste træning
        HomeResponse.LastTraining lastTraining = getLastTraining();

        return new HomeResponse(weekStats, lastTraining);
    }

    /**
     * Beregner statistik for de sidste 7 dage
     */
    private HomeResponse.WeekStats calculateWeekStats() {
        LocalDate today = LocalDate.now();
        LocalDate weekAgo = today.minusDays(7);

        // Hent alle sessions fra sidste uge
        List<TrainingSession> sessions = trainingSessionRepository.findByCreatedAtBetween(weekAgo, today);

        int totalTrainings = sessions.size();
        int totalSets = 0;
        double totalVolumeKg = 0.0;

        // Gennemgå alle sessions og beregn sets + volumen
        for (TrainingSession session : sessions) {
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
     * Henter seneste træning med formateret dato og note
     */
    private HomeResponse.LastTraining getLastTraining() {
        // Hent alle sessions sorteret efter dato (nyeste først)
        LocalDate today = LocalDate.now();
        List<TrainingSession> recentSessions = trainingSessionRepository
                .findByCreatedAtAfterOrderByCreatedAtDesc(today.minusMonths(1));

        if (recentSessions.isEmpty()) {
            return new HomeResponse.LastTraining("Ingen træning endnu", "");
        }

        TrainingSession lastSession = recentSessions.get(0);

        // Formater dato
        String formattedDate = formatTrainingDate(lastSession.getCreatedAt());

        // Hent note (eller default tekst)
        String note = lastSession.getNote() != null && !lastSession.getNote().isBlank()
                ? lastSession.getNote()
                : "Ingen noter";

        return new HomeResponse.LastTraining(formattedDate, note);
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
}