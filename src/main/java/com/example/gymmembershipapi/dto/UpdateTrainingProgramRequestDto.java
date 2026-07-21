package com.example.gymmembershipapi.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class UpdateTrainingProgramRequestDto {

    private String name;
    private String description;
    private Integer durationInWeeks;
    private BigDecimal monthlyPrice;
    private Long trainerId;
}