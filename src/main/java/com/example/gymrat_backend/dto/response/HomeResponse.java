package com.example.gymrat_backend.dto.response;

/*
Response DTO til forsiden
Indeholder ugestatistik og seneste træning
 */

public class HomeResponse {

    private WeekStats weekStats;
    private java.util.List<LastTraining> recentTrainings;
    private java.util.Map<String, TrainingDayData> trainingDays;

    // Konstruktør - uden args og med

    public HomeResponse() {
    }

    public HomeResponse(WeekStats weekStats, java.util.List<LastTraining> recentTrainings, java.util.Map<String, TrainingDayData> trainingDays) {
        this.weekStats = weekStats;
        this.recentTrainings = recentTrainings;
        this.trainingDays = trainingDays;
    }

    // Getter og setter

    public WeekStats getWeekStats() {
        return weekStats;
    }
    public void setWeekStats(WeekStats weekStats) {
        this.weekStats = weekStats;
    }

    public java.util.List<LastTraining> getRecentTrainings() {
        return recentTrainings;
    }
    public void setRecentTrainings(java.util.List<LastTraining> recentTrainings) {
        this.recentTrainings = recentTrainings;
    }

    public java.util.Map<String, TrainingDayData> getTrainingDays() {
        return trainingDays;
    }
    public void setTrainingDays(java.util.Map<String, TrainingDayData> trainingDays) {
        this.trainingDays = trainingDays;
    }

    // Nested classes

    public static class WeekStats {
        private Integer trainings;
        private Integer sets;
        private Double volumeKg;

        public WeekStats() {
        }

        public WeekStats(Integer trainings, Integer sets, Double volumeKg) {
            this.trainings = trainings;
            this.sets = sets;
            this.volumeKg = volumeKg;
        }

        public Integer getTrainings() {
            return trainings;
        }
        public void setTrainings(Integer trainings) {
            this.trainings = trainings;
        }

        public Integer getSets() {
            return sets;
        }
        public void setSets(Integer sets) {
            this.sets = sets;
        }

        public Double getVolumeKg() {
            return volumeKg;
        }
        public void setVolumeKg(Double volumeKg) {
            this.volumeKg = volumeKg;
        }
    }

    public static class LastTraining {
        private Long trainingSessionId;
        private String startedAt;
        private String completedAt;
        private String note;
        private Integer exerciseCount;

        public LastTraining() {
        }

        public LastTraining(Long trainingSessionId, String startedAt, String completedAt, String note, Integer exerciseCount) {
            this.trainingSessionId = trainingSessionId;
            this.startedAt = startedAt;
            this.completedAt = completedAt;
            this.note = note;
            this.exerciseCount = exerciseCount;
        }

        public Long getTrainingSessionId() {
            return trainingSessionId;
        }
        public void setTrainingSessionId(Long trainingSessionId) {
            this.trainingSessionId = trainingSessionId;
        }

        public String getStartedAt() {
            return startedAt;
        }
        public void setStartedAt(String startedAt) {
            this.startedAt = startedAt;
        }

        public String getCompletedAt() {
            return completedAt;
        }
        public void setCompletedAt(String completedAt) {
            this.completedAt = completedAt;
        }

        public String getNote() {
            return note;
        }
        public void setNote(String note) {
            this.note = note;
        }

        public Integer getExerciseCount() {
            return exerciseCount;
        }
        public void setExerciseCount(Integer exerciseCount) {
            this.exerciseCount = exerciseCount;
        }
    }

    public static class TrainingDayData {
        private Long trainingSessionId;
        private Double volumeKg;

        public TrainingDayData() {
        }

        public TrainingDayData(Long trainingSessionId, Double volumeKg) {
            this.trainingSessionId = trainingSessionId;
            this.volumeKg = volumeKg;
        }

        public Long getTrainingSessionId() {
            return trainingSessionId;
        }
        public void setTrainingSessionId(Long trainingSessionId) {
            this.trainingSessionId = trainingSessionId;
        }

        public Double getVolumeKg() {
            return volumeKg;
        }
        public void setVolumeKg(Double volumeKg) {
            this.volumeKg = volumeKg;
        }
    }

    @Override
    public String toString() {
        return "HomeResponse{" +
                "weekStats=" + weekStats +
                ", recentTrainings=" + recentTrainings +
                ", trainingDays=" + trainingDays +
                '}';
    }
}