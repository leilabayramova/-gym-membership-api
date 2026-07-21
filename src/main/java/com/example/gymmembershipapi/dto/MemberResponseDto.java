package com.example.gymmembershipapi.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberResponseDto {

    private Long id;
    private String fullName;
    private String email;
    private String phoneNumber;
    private LocalDate registrationDate;
}