package com.example.gymmembershipapi.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UpdateSubscriptionRequestDto {

    private Long memberId;
    private Long trainingProgramId;
    private LocalDate startDate;
    private LocalDate endDate;
}