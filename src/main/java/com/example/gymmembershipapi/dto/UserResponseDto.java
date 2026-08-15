package com.example.gymmembershipapi.dto;

import com.example.gymmembershipapi.entity.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Response model containing user information")
public class UserResponseDto {

    @Schema(description = "Unique identifier of the user", example = "1")
    private Long id;

    @Schema(description = "User's email address", example = "user@example.com")
    private String email;

    @Schema(description = "Role assigned to the user", example = "USER")
    private Role role;
}