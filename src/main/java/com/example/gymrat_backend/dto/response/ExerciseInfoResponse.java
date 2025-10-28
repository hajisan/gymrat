package com.example.gymrat_backend.dto.response;

/*
Response DTO med grundlæggende øvelses information.
Bruges som nested objekt i PerformedExerciseResponse
 */

public class ExerciseInfoResponse {

    private Long exerciseId;
    private String name;
    private String targetMuscleGroup;
    private String equipment;

    // Konstruktør - uden args og med

    public ExerciseInfoResponse() {
    }

    public ExerciseInfoResponse(Long exerciseId, String name, String targetMuscleGroup, String equipment) {
        this.exerciseId = exerciseId;
        this.name = name;
        this.targetMuscleGroup = targetMuscleGroup;
        this.equipment = equipment;
    }

    // Getter og Setter

    public Long getExerciseId() {
        return exerciseId;
    }
    public void setExerciseId(Long exerciseId) {
        this.exerciseId = exerciseId;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
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

    // toString

    @Override
    public String toString() {
        return "ExerciseInfoResponse{" +
                "exerciseId=" + exerciseId +
                ", name='" + name + '\'' +
                ", targetMuscleGroup='" + targetMuscleGroup + '\'' +
                ", equipment='" + equipment + '\'' +
                '}';
    }
}
