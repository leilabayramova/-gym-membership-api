package com.example.gymmembershipapi.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateSubscriptionRequestDto {

    private Long memberId;
    private Long trainingProgramId;
    private LocalDate startDate;
    private LocalDate endDate;
}