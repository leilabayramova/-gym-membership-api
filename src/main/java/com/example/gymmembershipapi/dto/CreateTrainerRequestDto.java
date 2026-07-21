package com.example.gymmembershipapi.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateTrainerRequestDto {

    private String fullName;
    private String email;
    private String phoneNumber;
    private String specialization;
}