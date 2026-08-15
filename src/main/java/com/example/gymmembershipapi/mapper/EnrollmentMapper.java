package com.example.gymmembershipapi.mapper;

import com.example.gymmembershipapi.dto.EnrollmentResponseDto;
import com.example.gymmembershipapi.entity.MemberEntity;
import com.example.gymmembershipapi.entity.SubscriptionEntity;

public interface EnrollmentMapper {

    static EnrollmentResponseDto toResponseDto(
            MemberEntity member,
            SubscriptionEntity subscription
    ) {
        return EnrollmentResponseDto.builder()
                .member(MemberMapper.toResponseDto(member))
                .subscription(
                        SubscriptionMapper.toResponseDto(subscription)
                )
                .build();
    }
}