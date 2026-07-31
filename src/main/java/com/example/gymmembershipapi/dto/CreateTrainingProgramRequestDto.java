package com.example.gymmembershipapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
@Schema(description = "Request model for creating a new training program")
public class CreateTrainingProgramRequestDto {

    @NotBlank(message = "Program name is required")
    @Size(
            min = 2,
            max = 100,
            message = "Program name must contain between 2 and 100 characters"
    )
    @Schema(
            description = "Name of the training program",
            example = "Strength Training"
    )
    private String name;

    @Size(
            max = 500,
            message = "Description must not exceed 500 characters"
    )
    @Schema(
            description = "Detailed description of the training program",
            example = "A training program focused on developing strength and endurance"
    )
    private String description;

    @NotNull(message = "Duration is required")
    @Positive(message = "Duration must be greater than zero")
    @Schema(
            description = "Duration of the training program in weeks",
            example = "12"
    )
    private Integer durationInWeeks;

    @NotNull(message = "Monthly price is required")
    @Positive(message = "Monthly price must be greater than zero")
    @Schema(
            description = "Monthly price of the training program",
            example = "79.99"
    )
    private BigDecimal monthlyPrice;

    @NotNull(message = "Trainer id is required")
    @Positive(message = "Trainer id must be greater than zero")
    @Schema(
            description = "Unique identifier of the trainer assigned to the program",
            example = "1"
    )
    private Long trainerId;

    @NotEmpty(message = "At least one category is required")
    @Schema(
            description = "Category ids assigned to the training program",
            example = "[1, 2]"
    )
    private Set<@Positive Long> categoryIds;
}