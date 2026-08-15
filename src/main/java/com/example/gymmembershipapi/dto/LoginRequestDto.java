package com.example.gymmembershipapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Request model for user login")
public class LoginRequestDto {

    @NotBlank(message = "Email is required")
    @Email(message = "Email format is invalid")
    @Schema(
            description = "User's registered email address",
            example = "user@example.com"
    )
    private String email;

    @NotBlank(message = "Password is required")
    @Schema(
            description = "User's password",
            example = "StrongPass123!"
    )
    private String password;
}