package com.example.gymmembershipapi.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CreateSubscriptionRequestDto {

    @NotNull(message = "Member id is required")
    @Positive(message = "Member id must be greater than zero")
    private Long memberId;

    @NotNull(message = "Training program id is required")
    @Positive(message = "Training program id must be greater than zero")
    private Long trainingProgramId;

    @NotNull(message = "Start date is required")
    private LocalDate startDate;

    @NotNull(message = "End date is required")
    private LocalDate endDate;
}