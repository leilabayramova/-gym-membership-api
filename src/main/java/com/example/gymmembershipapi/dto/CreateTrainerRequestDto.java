package com.example.gymmembershipapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Trainer creation request")
public class CreateTrainerRequestDto {

    @NotBlank
    @Schema(
            description = "Trainer's full name",
            example = "Aysel Mammadova"
    )
    private String fullName;

    @NotBlank
    @Email
    @Schema(
            description = "Trainer's unique email address",
            example = "aysel.mammadova@gmail.com"
    )
    private String email;

    @NotBlank
    @Schema(
            description = "Trainer's phone number",
            example = "+994501234567"
    )
    private String phoneNumber;

    @NotBlank
    @Schema(
            description = "Trainer's specialization",
            example = "Fitness"
    )
    private String specialization;
}