package com.example.gymmembershipapi.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateMemberRequestDto {

    private String fullName;
    private String email;
    private String phoneNumber;
}