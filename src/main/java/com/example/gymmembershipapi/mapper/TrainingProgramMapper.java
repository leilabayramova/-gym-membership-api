package com.example.gymmembershipapi.mapper;

import com.example.gymmembershipapi.dto.CreateTrainingProgramRequestDto;
import com.example.gymmembershipapi.dto.TrainingProgramResponseDto;
import com.example.gymmembershipapi.dto.UpdateTrainingProgramRequestDto;
import com.example.gymmembershipapi.entity.CategoryEntity;
import com.example.gymmembershipapi.entity.TrainerEntity;
import com.example.gymmembershipapi.entity.TrainingProgramEntity;

import java.util.Set;

public interface TrainingProgramMapper {

    static TrainingProgramEntity toEntity(
            CreateTrainingProgramRequestDto requestDto,
            TrainerEntity trainer,
            Set<CategoryEntity> categories
    ) {
        return TrainingProgramEntity.builder()
                .name(requestDto.getName())
                .description(requestDto.getDescription())
                .durationInWeeks(requestDto.getDurationInWeeks())
                .monthlyPrice(requestDto.getMonthlyPrice())
                .trainer(trainer)
                .categories(categories)
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
                .categoryIds(
                        trainingProgram.getCategories()
                                .stream()
                                .map(CategoryEntity::getId)
                                .collect(java.util.stream.Collectors.toSet())
                )
                .build();
    }

    static void updateEntity(
            TrainingProgramEntity trainingProgram,
            UpdateTrainingProgramRequestDto requestDto,
            TrainerEntity trainer
    ) {
        trainingProgram.setName(requestDto.getName());
        trainingProgram.setDescription(requestDto.getDescription());
        trainingProgram.setDurationInWeeks(requestDto.getDurationInWeeks());
        trainingProgram.setMonthlyPrice(requestDto.getMonthlyPrice());
        trainingProgram.setTrainer(trainer);
    }
}