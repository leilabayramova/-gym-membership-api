package com.example.gymmembershipapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Trainer response model")
public class TrainerResponseDto {

    @Schema(description = "Trainer ID", example = "1")
    private Long id;

    @Schema(description = "Trainer's full name", example = "Aysel Mammadova")
    private String fullName;

    @Schema(description = "Trainer's email", example = "aysel.mammadova@gmail.com")
    private String email;

    @Schema(description = "Trainer's phone number", example = "+994501234567")
    private String phoneNumber;

    @Schema(description = "Trainer's specialization", example = "Fitness")
    private String specialization;
}