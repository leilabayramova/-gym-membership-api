package com.example.gymmembershipapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Response model containing subscription information")
public class SubscriptionResponseDto {

    @Schema(
            description = "Unique identifier of the subscription",
            example = "1"
    )
    private Long id;

    @Schema(
            description = "Subscription start date",
            example = "2026-08-01"
    )
    private LocalDate startDate;

    @Schema(
            description = "Subscription end date",
            example = "2026-10-01"
    )
    private LocalDate endDate;

    @Schema(
            description = "Indicates whether the subscription is active",
            example = "true"
    )
    private Boolean active;

    @Schema(
            description = "Unique identifier of the subscribed member",
            example = "1"
    )
    private Long memberId;

    @Schema(
            description = "Unique identifier of the selected training program",
            example = "1"
    )
    private Long trainingProgramId;
}