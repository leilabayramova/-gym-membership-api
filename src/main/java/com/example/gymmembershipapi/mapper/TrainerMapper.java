package com.example.gymmembershipapi.mapper;


import com.example.gymmembershipapi.dto.CreateTrainerRequestDto;
import com.example.gymmembershipapi.dto.TrainerResponseDto;
import com.example.gymmembershipapi.dto.UpdateTrainerRequestDto;
import com.example.gymmembershipapi.entity.TrainerEntity;

public interface TrainerMapper {

    static TrainerEntity toEntity(CreateTrainerRequestDto requestDto) {
        return TrainerEntity.builder()
                .fullName(requestDto.getFullName())
                .email(requestDto.getEmail())
                .phoneNumber(requestDto.getPhoneNumber())
                .specialization(requestDto.getSpecialization())
                .build();
    }

    static TrainerResponseDto toResponseDto(TrainerEntity trainer) {
        return TrainerResponseDto.builder()
                .id(trainer.getId())
                .fullName(trainer.getFullName())
                .email(trainer.getEmail())
                .phoneNumber(trainer.getPhoneNumber())
                .specialization(trainer.getSpecialization())
                .build();
    }

    static void updateEntity(
            TrainerEntity trainer,
            UpdateTrainerRequestDto requestDto
    ) {
        trainer.setFullName(requestDto.getFullName());
        trainer.setEmail(requestDto.getEmail());
        trainer.setPhoneNumber(requestDto.getPhoneNumber());
        trainer.setSpecialization(requestDto.getSpecialization());
    }
}