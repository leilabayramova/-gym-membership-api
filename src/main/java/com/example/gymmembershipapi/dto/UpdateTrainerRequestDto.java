package com.example.gymmembershipapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Request model for updating an existing trainer")
public class UpdateTrainerRequestDto {

    @NotBlank(message = "Full name is required")
    @Size(
            min = 2,
            max = 100,
            message = "Full name must contain between 2 and 100 characters"
    )
    @Schema(
            description = "Updated full name of the trainer",
            example = "Aysel Mammadova"
    )
    private String fullName;

    @NotBlank(message = "Email is required")
    @Email(message = "Email format is invalid")
    @Schema(
            description = "Updated unique email address of the trainer",
            example = "aysel.mammadova@example.com"
    )
    private String email;

    @NotBlank(message = "Phone number is required")
    @Schema(
            description = "Updated phone number of the trainer",
            example = "+994501234567"
    )
    private String phoneNumber;

    @NotBlank(message = "Specialization is required")
    @Schema(
            description = "Updated specialization of the trainer",
            example = "Strength Training"
    )
    private String specialization;
}