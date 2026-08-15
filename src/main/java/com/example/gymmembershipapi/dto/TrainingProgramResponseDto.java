package com.example.gymmembershipapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Response model containing training program information")
public class TrainingProgramResponseDto {

    @Schema(
            description = "Unique identifier of the training program",
            example = "1"
    )
    private Long id;

    @Schema(
            description = "Name of the training program",
            example = "Strength Training"
    )
    private String name;

    @Schema(
            description = "Detailed description of the training program",
            example = "A training program focused on developing strength and endurance"
    )
    private String description;

    @Schema(
            description = "Duration of the training program in weeks",
            example = "12"
    )
    private Integer durationInWeeks;

    @Schema(
            description = "Monthly price of the training program",
            example = "79.99"
    )
    private BigDecimal monthlyPrice;

    @Schema(
            description = "Unique identifier of the trainer assigned to the program",
            example = "1"
    )
    private Long trainerId;

    @Schema(
            description = "Category ids assigned to the training program",
            example = "[1, 2]"
    )
    private Set<Long> categoryIds;
}