package com.example.gymrat_backend.model;

/**
 * Enum der definerer hvordan en øvelse måles
 */
public enum ExerciseType {
    REP_BASED,      // Øvelser målt i gentagelser (reps) - f.eks. Squats, Push-ups
    DURATION_BASED, // Øvelser målt i tid (sekunder) - f.eks. Plank, Wall sit
    BOTH            // Øvelser der kan måles begge veje (sjældent, men flexibelt)
}
