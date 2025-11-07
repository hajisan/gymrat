package com.example.gymrat_backend.mapper;

import com.example.gymrat_backend.dto.response.ExerciseInfoResponse;
import com.example.gymrat_backend.model.Exercise;

public class ExerciseMapper {

    /*
    Privat konstruktør til at forhindre instantiering af denne utility-klasse.
    - Denne klasse indeholder kun statiske metoder til at mappe mellem Entity og DTO.
    - Den skal derfor aldrig instantieres - bruger i stedet ExerciseMapper.toInfoResponse(...)

    Hvis nogen forsøger at oprette et objekt af klassen (f.eks. via reflection),
    kastes en UnsupportedOperationException for at signalere fejlen tydeligt.
     */

    private ExerciseMapper() {
        throw new UnsupportedOperationException("Utility class");
    }

    // Exercise -> ExerciseInfoResponse
    public static ExerciseInfoResponse toInfoResponse(Exercise exercise) {
        if (exercise == null) return null;

        ExerciseInfoResponse response = new ExerciseInfoResponse();
        response.setExerciseId(exercise.getExerciseId());
        response.setName(exercise.getName());
        response.setTargetMuscleGroup(exercise.getTargetMuscleGroup());
        response.setEquipment(exercise.getEquipment());

        return response;
    }
}
