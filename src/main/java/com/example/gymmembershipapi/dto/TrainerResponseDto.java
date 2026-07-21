package com.example.gymmembershipapi.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrainerResponseDto {

    private Long id;
    private String fullName;
    private String email;
    private String phoneNumber;
    private String specialization;
}