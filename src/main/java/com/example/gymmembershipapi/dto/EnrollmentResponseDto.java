package com.example.gymmembershipapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Response containing the created member and subscription")
public class EnrollmentResponseDto {

    private MemberResponseDto member;

    private SubscriptionResponseDto subscription;
}