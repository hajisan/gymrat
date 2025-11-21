package com.example.gymrat_backend.dto.response;

/*
Response DTO for en øvelse i aktiv træning
Inkluderer "last time" data for reference
 */

import java.util.ArrayList;
import java.util.List;

public class WorkoutExerciseResponse {

    private Long performedExerciseId;
    private Long exerciseId;
    private String exerciseName;
    private String targetMuscleGroup;
    private String equipment;
    private Integer orderNumber;
    private List<WorkoutSetResponse> sets = new ArrayList<>();
    private LastPerformedData lastPerformed; // Reference til sidste gang

    // Konstruktør - uden args og med

    public WorkoutExerciseResponse() {
    }

    public WorkoutExerciseResponse(Long performedExerciseId, Long exerciseId, String exerciseName,
                                   String targetMuscleGroup, String equipment, Integer orderNumber,
                                   List<WorkoutSetResponse> sets, LastPerformedData lastPerformed) {
        this.performedExerciseId = performedExerciseId;
        this.exerciseId = exerciseId;
        this.exerciseName = exerciseName;
        this.targetMuscleGroup = targetMuscleGroup;
        this.equipment = equipment;
        this.orderNumber = orderNumber;
        this.sets = sets;
        this.lastPerformed = lastPerformed;
    }

    // Getter og setter

    public Long getPerformedExerciseId() {
        return performedExerciseId;
    }
    public void setPerformedExerciseId(Long performedExerciseId) {
        this.performedExerciseId = performedExerciseId;
    }

    public Long getExerciseId() {
        return exerciseId;
    }
    public void setExerciseId(Long exerciseId) {
        this.exerciseId = exerciseId;
    }

    public String getExerciseName() {
        return exerciseName;
    }
    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public String getTargetMuscleGroup() {
        return targetMuscleGroup;
    }
    public void setTargetMuscleGroup(String targetMuscleGroup) {
        this.targetMuscleGroup = targetMuscleGroup;
    }

    public String getEquipment() {
        return equipment;
    }
    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }

    public Integer getOrderNumber() {
        return orderNumber;
    }
    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }

    public List<WorkoutSetResponse> getSets() {
        return sets;
    }
    public void setSets(List<WorkoutSetResponse> sets) {
        this.sets = sets;
    }

    public LastPerformedData getLastPerformed() {
        return lastPerformed;
    }
    public void setLastPerformed(LastPerformedData lastPerformed) {
        this.lastPerformed = lastPerformed;
    }

    // Nested class for last performed data
    public static class LastPerformedData {
        private Double averageWeight;
        private Integer averageReps;
        private Integer averageDuration;

        public LastPerformedData() {
        }

        public LastPerformedData(Double averageWeight, Integer averageReps, Integer averageDuration) {
            this.averageWeight = averageWeight;
            this.averageReps = averageReps;
            this.averageDuration = averageDuration;
        }

        public Double getAverageWeight() {
            return averageWeight;
        }
        public void setAverageWeight(Double averageWeight) {
            this.averageWeight = averageWeight;
        }

        public Integer getAverageReps() {
            return averageReps;
        }
        public void setAverageReps(Integer averageReps) {
            this.averageReps = averageReps;
        }

        public Integer getAverageDuration() {
            return averageDuration;
        }
        public void setAverageDuration(Integer averageDuration) {
            this.averageDuration = averageDuration;
        }
    }

    @Override
    public String toString() {
        return "WorkoutExerciseResponse{" +
                "performedExerciseId=" + performedExerciseId +
                ", exerciseName='" + exerciseName + '\'' +
                ", orderNumber=" + orderNumber +
                ", sets=" + sets +
                ", lastPerformed=" + lastPerformed +
                '}';
    }
}