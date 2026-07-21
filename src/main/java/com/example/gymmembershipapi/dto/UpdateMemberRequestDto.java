package com.example.gymmembershipapi.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateMemberRequestDto {

    private String fullName;
    private String email;
    private String phoneNumber;
}