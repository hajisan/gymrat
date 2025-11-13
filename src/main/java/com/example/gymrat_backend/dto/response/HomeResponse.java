package com.example.gymrat_backend.dto.response;

/*
Response DTO til forsiden
Indeholder ugestatistik og seneste træning
 */

public class HomeResponse {

    private WeekStats weekStats;
    private LastTraining lastTraining;

    // Konstruktør - uden args og med

    public HomeResponse() {
    }

    public HomeResponse(WeekStats weekStats, LastTraining lastTraining) {
        this.weekStats = weekStats;
        this.lastTraining = lastTraining;
    }

    // Getter og setter

    public WeekStats getWeekStats() {
        return weekStats;
    }
    public void setWeekStats(WeekStats weekStats) {
        this.weekStats = weekStats;
    }

    public LastTraining getLastTraining() {
        return lastTraining;
    }
    public void setLastTraining(LastTraining lastTraining) {
        this.lastTraining = lastTraining;
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
        private String date;
        private String note;

        public LastTraining() {
        }

        public LastTraining(String date, String note) {
            this.date = date;
            this.note = note;
        }

        public String getDate() {
            return date;
        }
        public void setDate(String date) {
            this.date = date;
        }

        public String getNote() {
            return note;
        }
        public void setNote(String note) {
            this.note = note;
        }
    }

    @Override
    public String toString() {
        return "HomeResponse{" +
                "weekStats=" + weekStats +
                ", lastTraining=" + lastTraining +
                '}';
    }
}