package com.example.gymmembershipapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Schema(description = "Request model for updating an existing subscription")
public class UpdateSubscriptionRequestDto {

    @NotNull(message = "Start date is required")
    @Schema(
            description = "Updated subscription start date",
            example = "2026-08-01"
    )
    private LocalDate startDate;

    @NotNull(message = "End date is required")
    @Schema(
            description = "Updated subscription end date",
            example = "2026-11-01"
    )
    private LocalDate endDate;

    @NotNull(message = "Member id is required")
    @Positive(message = "Member id must be greater than zero")
    @Schema(
            description = "Unique identifier of the member",
            example = "1"
    )
    private Long memberId;

    @NotNull(message = "Training program id is required")
    @Positive(message = "Training program id must be greater than zero")
    @Schema(
            description = "Unique identifier of the training program",
            example = "1"
    )
    private Long trainingProgramId;
}