package com.example.gymmembershipapi.mapper;

import com.example.gymmembershipapi.dto.CreateSubscriptionRequestDto;
import com.example.gymmembershipapi.dto.SubscriptionResponseDto;
import com.example.gymmembershipapi.dto.UpdateSubscriptionRequestDto;
import com.example.gymmembershipapi.entity.MemberEntity;
import com.example.gymmembershipapi.entity.SubscriptionEntity;
import com.example.gymmembershipapi.entity.TrainingProgramEntity;

public interface SubscriptionMapper {

    static SubscriptionEntity toEntity(
            CreateSubscriptionRequestDto requestDto,
            MemberEntity member,
            TrainingProgramEntity trainingProgram
    ) {
        return SubscriptionEntity.builder()
                .startDate(requestDto.getStartDate())
                .endDate(requestDto.getEndDate())
                .active(true)
                .member(member)
                .trainingProgram(trainingProgram)
                .build();
    }

    static SubscriptionResponseDto toResponseDto(
            SubscriptionEntity subscription
    ) {
        return SubscriptionResponseDto.builder()
                .id(subscription.getId())
                .startDate(subscription.getStartDate())
                .endDate(subscription.getEndDate())
                .active(subscription.getActive())
                .memberId(subscription.getMember().getId())
                .trainingProgramId(subscription.getTrainingProgram().getId())
                .build();
    }
    static void updateEntity(
            SubscriptionEntity subscription,
            UpdateSubscriptionRequestDto requestDto,
            MemberEntity member,
            TrainingProgramEntity trainingProgram
    ) {
        subscription.setMember(member);
        subscription.setTrainingProgram(trainingProgram);
        subscription.setStartDate(requestDto.getStartDate());
        subscription.setEndDate(requestDto.getEndDate());
    }
}