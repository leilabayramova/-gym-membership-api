package com.example.gymmembershipapi.mapper;

import com.example.gymmembershipapi.dto.CreateTrainingProgramRequestDto;
import com.example.gymmembershipapi.dto.TrainingProgramResponseDto;
import com.example.gymmembershipapi.entity.TrainerEntity;
import com.example.gymmembershipapi.entity.TrainingProgramEntity;

public interface TrainingProgramMapper {

    static TrainingProgramEntity toEntity(
            CreateTrainingProgramRequestDto requestDto,
            TrainerEntity trainer
    ) {
        return TrainingProgramEntity.builder()
                .name(requestDto.getName())
                .description(requestDto.getDescription())
                .durationInWeeks(requestDto.getDurationInWeeks())
                .monthlyPrice(requestDto.getMonthlyPrice())
                .trainer(trainer)
                .build();
    }

    static TrainingProgramResponseDto toResponseDto(
            TrainingProgramEntity trainingProgram
    ) {
        return TrainingProgramResponseDto.builder()
                .id(trainingProgram.getId())
                .name(trainingProgram.getName())
                .description(trainingProgram.getDescription())
                .durationInWeeks(trainingProgram.getDurationInWeeks())
                .monthlyPrice(trainingProgram.getMonthlyPrice())
                .trainerId(trainingProgram.getTrainer().getId())
                .build();
    }
}