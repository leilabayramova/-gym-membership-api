package com.example.gymmembershipapi.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateTrainerRequestDto {

    @NotBlank(message = "Full name is required")
    @Size(min = 2, max = 100,
            message = "Full name must contain 2-100 characters")
    private String fullName;

    @NotBlank(message = "Email is required")
    @Email(message = "Email format is invalid")
    private String email;

    @NotBlank(message = "Phone number is required")
    private String phoneNumber;

    @NotBlank(message = "Specialization is required")
    private String specialization;
}