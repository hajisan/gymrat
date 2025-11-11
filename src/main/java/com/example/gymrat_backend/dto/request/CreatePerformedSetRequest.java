package com.example.gymrat_backend.dto.request;

import com.example.gymrat_backend.model.SideOfBody;
import com.example.gymrat_backend.validation.RepsOrDuration;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

/*
Request DTO til at oprette et nyt performed set.
Mindst én af 'reps' eller 'durationSeconds' skal være sat (valideres via custom annotation)
 */
@RepsOrDuration // Custom validator
public class CreatePerformedSetRequest {

    @NotNull(message = "Side of body is required")
    private SideOfBody sideOfBody;

    @NotNull(message = "Set number is required")
    @Min(value = 1, message = "Set number must be at least 1")
    private Integer setNumber;

    @DecimalMin(value = "0.0", inclusive = false, message = "Weight must be greater then 0.")
    private BigDecimal weight;

    @Min(value = 1, message = "Reps must be at least 1")
    private Integer reps;

    @Min(value = 1, message = "Duration must be a least 1 second")
    private Integer durationSeconds;

    @Size(max = 255, message = "Note cannot exceed 255 characters")
    private String note;

    // Konstruktør - uden args og med

    public CreatePerformedSetRequest() {}

    public CreatePerformedSetRequest(SideOfBody sideOfBody, Integer setNumber, BigDecimal weight,
                                     Integer reps, Integer durationSeconds, String note) {
        this.sideOfBody = sideOfBody;
        this.setNumber = setNumber;
        this.weight = weight;
        this.reps = reps;
        this.durationSeconds = durationSeconds;
        this.note = note;
    }

    // Getter og setter

    public SideOfBody getSideOfBody() {
        return sideOfBody;
    }
    public void setSideOfBody(SideOfBody sideOfBody) {
        this.sideOfBody = sideOfBody;
    }

    public Integer getSetNumber() {
        return setNumber;
    }
    public void setSetNumber(Integer setNumber) {
        this.setNumber = setNumber;
    }

    public BigDecimal getWeight() {
        return weight;
    }
    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public Integer getReps() {
        return reps;
    }
    public void setReps(Integer reps) {
        this.reps = reps;
    }

    public Integer getDurationSeconds() {
        return durationSeconds;
    }
    public void setDurationSeconds(Integer durationSeconds) {
        this.durationSeconds = durationSeconds;
    }

    public String getNote() {
        return note;
    }
    public void setNote(String note) {
        this.note = note;
    }

    // toString

    @Override
    public String toString() {
        return "CreatePerformedSetRequest{" +
                "sideOfBody=" + sideOfBody +
                ", setNumber=" + setNumber +
                ", weight=" + weight +
                ", reps=" + reps +
                ", durationSeconds=" + durationSeconds +
                ", note='" + note + '\'' +
                '}';
    }
}
