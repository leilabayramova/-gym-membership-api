package com.example.gymmembershipapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Schema(description = "Request model for updating an existing training program")
public class UpdateTrainingProgramRequestDto {

    @NotBlank(message = "Program name is required")
    @Size(
            min = 2,
            max = 100,
            message = "Program name must contain between 2 and 100 characters"
    )
    @Schema(
            description = "Updated name of the training program",
            example = "Advanced Strength Training"
    )
    private String name;

    @Size(
            max = 500,
            message = "Description must not exceed 500 characters"
    )
    @Schema(
            description = "Updated description of the training program",
            example = "An advanced program focused on strength and muscle development"
    )
    private String description;

    @NotNull(message = "Duration is required")
    @Positive(message = "Duration must be greater than zero")
    @Schema(
            description = "Updated duration of the training program in weeks",
            example = "16"
    )
    private Integer durationInWeeks;

    @NotNull(message = "Monthly price is required")
    @Positive(message = "Monthly price must be greater than zero")
    @Schema(
            description = "Updated monthly price of the training program",
            example = "99.99"
    )
    private BigDecimal monthlyPrice;

    @NotNull(message = "Trainer id is required")
    @Positive(message = "Trainer id must be greater than zero")
    @Schema(
            description = "Unique identifier of the trainer assigned to the program",
            example = "1"
    )
    private Long trainerId;
}