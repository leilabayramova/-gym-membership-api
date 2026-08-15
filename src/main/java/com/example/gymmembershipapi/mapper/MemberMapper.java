package com.example.gymmembershipapi.mapper;

import com.example.gymmembershipapi.dto.CreateMemberRequestDto;
import com.example.gymmembershipapi.dto.MemberResponseDto;
import com.example.gymmembershipapi.dto.UpdateMemberRequestDto;
import com.example.gymmembershipapi.entity.MemberEntity;

import java.time.LocalDate;

public interface MemberMapper {

    static MemberEntity toEntity(CreateMemberRequestDto requestDto) {
        return MemberEntity.builder()
                .fullName(requestDto.getFullName())
                .email(requestDto.getEmail())
                .phoneNumber(requestDto.getPhoneNumber())
                .registrationDate(LocalDate.now())
                .build();
    }

    static MemberResponseDto toResponseDto(MemberEntity member) {
        return MemberResponseDto.builder()
                .id(member.getId())
                 .fullName(member.getFullName())
                .email(member.getEmail())
                .phoneNumber(member.getPhoneNumber())
                .registrationDate(member.getRegistrationDate())
                .build();
    }
    static void updateEntity(
            MemberEntity member,
            UpdateMemberRequestDto requestDto
    ) {
        member.setFullName(requestDto.getFullName());
        member.setEmail(requestDto.getEmail());
        member.setPhoneNumber(requestDto.getPhoneNumber());
    }
}