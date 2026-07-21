package com.example.gymmembershipapi.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateTrainingProgramRequestDto {

    private String name;
    private String description;
    private Integer durationInWeeks;
    private BigDecimal monthlyPrice;
    private Long trainerId;
}