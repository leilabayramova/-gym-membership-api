package com.example.gymmembershipapi.dto;

import com.example.gymmembershipapi.entity.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Response model returned after successful authentication")
public class AuthResponseDto {

    @Schema(
            description = "JWT access token",
            example = "eyJhbGciOiJIUzI1NiJ9..."
    )
    private String accessToken;

    @Builder.Default
    @Schema(
            description = "Authentication scheme used with the token",
            example = "Bearer"
    )
    private String tokenType = "Bearer";

    @Schema(
            description = "Authenticated user's email address",
            example = "user@example.com"
    )
    private String email;

    @Schema(
            description = "Role assigned to the authenticated user",
            example = "USER"
    )
    private Role role;
}