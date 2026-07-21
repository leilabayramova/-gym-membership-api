package com.example.gymmembershipapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class UpdateTrainingProgramRequestDto {

    @NotBlank(message = "Program name is required")
    private String name;

    @NotBlank(message = "Description is required")
    private String description;

    @NotNull(message = "Duration is required")
    @Positive(message = "Duration must be greater than zero")
    private Integer durationInWeeks;

    @NotNull(message = "Monthly price is required")
    @Positive(message = "Monthly price must be greater than zero")
    private BigDecimal monthlyPrice;

    @NotNull(message = "Trainer id is required")
    @Positive(message = "Trainer id must be greater than zero")
    private Long trainerId;
}