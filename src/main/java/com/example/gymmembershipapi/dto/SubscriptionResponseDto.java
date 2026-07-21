package com.example.gymmembershipapi.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionResponseDto {

    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;
    private Boolean active;
    private Long memberId;
    private Long trainingProgramId;
}