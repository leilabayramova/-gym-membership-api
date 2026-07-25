package com.example.gymmembershipapi.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Builder
@Schema(description = "Standard API error response")
public class ErrorResponse {

    @Schema(
            description = "Date and time when the error occurred",
            example = "2026-07-25T15:30:00"
    )
    private LocalDateTime timestamp;

    @Schema(
            description = "HTTP status code",
            example = "404"
    )
    private int status;

    @Schema(
            description = "HTTP error name",
            example = "Not Found"
    )
    private String error;

    @Schema(
            description = "Detailed error message",
            example = "Trainer not found with id: 1"
    )
    private String message;

    @Schema(
            description = "Request path where the error occurred",
            example = "/api/trainers/1"
    )
    private String path;

    @Schema(
            description = "Validation errors associated with request fields",
            example = "{\"email\":\"Email format is invalid\",\"fullName\":\"Full name is required\"}"
    )
    private Map<String, String> validationErrors;
}