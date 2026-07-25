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
@Schema(description = "Request model for updating an existing member")
public class UpdateMemberRequestDto {

    @NotBlank(message = "Full name is required")
    @Size(
            min = 2,
            max = 100,
            message = "Full name must contain between 2 and 100 characters"
    )
    @Schema(
            description = "Updated full name of the member",
            example = "Leyla Bayramova"
    )
    private String fullName;

    @NotBlank(message = "Email is required")
    @Email(message = "Email format is invalid")
    @Schema(
            description = "Updated unique email address of the member",
            example = "leyla.bayramova@example.com"
    )
    private String email;

    @NotBlank(message = "Phone number is required")
    @Pattern(
            regexp = "^\\+?[0-9]{9,15}$",
            message = "Phone number format is invalid"
    )
    @Schema(
            description = "Updated phone number of the member",
            example = "+994503788541"
    )
    private String phoneNumber;
}