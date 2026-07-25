package com.example.gymmembershipapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Request model for creating a new member")
public class CreateMemberRequestDto {

    @NotBlank(message = "Full name is required")
    @Size(
            min = 2,
            max = 100,
            message = "Full name must contain between 2 and 100 characters"
    )
    @Schema(
            description = "Member's full name",
            example = "Leyla Bayramova"
    )
    private String fullName;

    @NotBlank(message = "Email is required")
    @Email(message = "Email format is invalid")
    @Schema(
            description = "Member's unique email address",
            example = "leyla.bayramova@example.com"
    )
    private String email;

    @NotBlank(message = "Phone number is required")
    @Pattern(
            regexp = "^\\+?[0-9]{9,15}$",
            message = "Phone number format is invalid"
    )
    @Schema(
            description = "Member's phone number",
            example = "+994503788541"
    )
    private String phoneNumber;
}