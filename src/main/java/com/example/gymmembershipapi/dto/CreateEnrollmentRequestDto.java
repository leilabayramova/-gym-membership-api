package com.example.gymmembershipapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Schema(description = "Request model for creating a member with an initial subscription")
public class CreateEnrollmentRequestDto {

    @Valid
    @NotNull(message = "Member information is required")
    private CreateMemberRequestDto member;

    @NotNull(message = "Start date is required")
    @Schema(example = "2026-08-03")
    private LocalDate startDate;

    @NotNull(message = "End date is required")
    @Schema(example = "2026-10-03")
    private LocalDate endDate;

    @NotNull(message = "Training program id is required")
    @Positive(message = "Training program id must be greater than zero")
    @Schema(example = "1")
    private Long trainingProgramId;

    @AssertTrue(message = "End date must be after start date")
    public boolean isDateRangeValid() {
        return startDate == null
                || endDate == null
                || endDate.isAfter(startDate);
    }
}