package com.example.gymmembershipapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Response model containing member information")
public class MemberResponseDto {

    @Schema(
            description = "Unique identifier of the member",
            example = "1"
    )
    private Long id;

    @Schema(
            description = "Member's full name",
            example = "Leyla Bayramova"
    )
    private String fullName;

    @Schema(
            description = "Member's email address",
            example = "leyla.bayramova@example.com"
    )
    private String email;

    @Schema(
            description = "Member's phone number",
            example = "+994503788541"
    )
    private String phoneNumber;

    @Schema(
            description = "Date when the member was registered",
            example = "2026-07-25"
    )
    private LocalDate registrationDate;
}