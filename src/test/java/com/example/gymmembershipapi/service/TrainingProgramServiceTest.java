package com.example.gymmembershipapi.service;

import com.example.gymmembershipapi.dto.CreateTrainingProgramRequestDto;
import com.example.gymmembershipapi.dto.TrainingProgramResponseDto;
import com.example.gymmembershipapi.dto.UpdateTrainingProgramRequestDto;
import com.example.gymmembershipapi.entity.CategoryEntity;
import com.example.gymmembershipapi.entity.TrainerEntity;
import com.example.gymmembershipapi.entity.TrainingProgramEntity;
import com.example.gymmembershipapi.exception.ResourceNotFoundException;
import com.example.gymmembershipapi.repository.CategoryRepository;
import com.example.gymmembershipapi.repository.TrainerRepository;
import com.example.gymmembershipapi.repository.TrainingProgramRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TrainingProgramServiceTest {

    @Mock
    private TrainingProgramRepository trainingProgramRepository;

    @Mock
    private TrainerRepository trainerRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private TrainingProgramService trainingProgramService;

    @Test
    void create_shouldCreateTrainingProgram() {
        CreateTrainingProgramRequestDto requestDto =
                new CreateTrainingProgramRequestDto();

        requestDto.setName("Fitness Program");
        requestDto.setDescription("Beginner fitness program");
        requestDto.setDurationInWeeks(8);
        requestDto.setMonthlyPrice(new BigDecimal("120.00"));
        requestDto.setTrainerId(1L);
        requestDto.setCategoryIds(Set.of(1L, 2L));

        TrainerEntity trainer = TrainerEntity.builder()
                .id(1L)
                .fullName("Ali Mammadov")
                .email("ali@example.com")
                .phoneNumber("0501111111")
                .specialization("Fitness")
                .build();

        CategoryEntity cardioCategory = CategoryEntity.builder()
                .id(1L)
                .name("CARDIO")
                .build();

        CategoryEntity strengthCategory = CategoryEntity.builder()
                .id(2L)
                .name("STRENGTH")
                .build();

        Set<CategoryEntity> categories =
                Set.of(cardioCategory, strengthCategory);

        TrainingProgramEntity savedTrainingProgram =
                TrainingProgramEntity.builder()
                        .id(1L)
                        .name("Fitness Program")
                        .description("Beginner fitness program")
                        .durationInWeeks(8)
                        .monthlyPrice(new BigDecimal("120.00"))
                        .trainer(trainer)
                        .categories(categories)
                        .build();

        when(trainerRepository.findById(1L))
                .thenReturn(Optional.of(trainer));

        when(categoryRepository.findAllById(requestDto.getCategoryIds()))
                .thenReturn(List.of(cardioCategory, strengthCategory));

        when(trainingProgramRepository.save(
                any(TrainingProgramEntity.class)
        )).thenReturn(savedTrainingProgram);

        TrainingProgramResponseDto response =
                trainingProgramService.create(requestDto);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Fitness Program", response.getName());
        assertEquals(
                new BigDecimal("120.00"),
                response.getMonthlyPrice()
        );
        assertEquals(Set.of(1L, 2L), response.getCategoryIds());

        verify(trainerRepository).findById(1L);
        verify(categoryRepository)
                .findAllById(requestDto.getCategoryIds());
        verify(trainingProgramRepository)
                .save(any(TrainingProgramEntity.class));
    }

    @Test
    void create_shouldThrowExceptionWhenTrainerDoesNotExist() {
        CreateTrainingProgramRequestDto requestDto =
                new CreateTrainingProgramRequestDto();

        requestDto.setTrainerId(99L);

        when(trainerRepository.findById(99L))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> trainingProgramService.create(requestDto)
        );

        verify(categoryRepository, never())
                .findAllById(any());

        verify(trainingProgramRepository, never())
                .save(any(TrainingProgramEntity.class));
    }

    @Test
    void getById_shouldReturnTrainingProgram() {
        TrainerEntity trainer = TrainerEntity.builder()
                .id(1L)
                .fullName("Ali Mammadov")
                .build();

        TrainingProgramEntity trainingProgram =
                TrainingProgramEntity.builder()
                        .id(1L)
                        .name("Fitness Program")
                        .description("Beginner fitness program")
                        .durationInWeeks(8)
                        .monthlyPrice(new BigDecimal("120.00"))
                        .trainer(trainer)
                        .build();

        when(trainingProgramRepository.findById(1L))
                .thenReturn(Optional.of(trainingProgram));

        TrainingProgramResponseDto response =
                trainingProgramService.getById(1L);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Fitness Program", response.getName());
    }

    @Test
    void getById_shouldThrowExceptionWhenProgramDoesNotExist() {
        when(trainingProgramRepository.findById(99L))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> trainingProgramService.getById(99L)
        );
    }

    @Test
    void update_shouldUpdateTrainingProgram() {
        TrainerEntity oldTrainer = TrainerEntity.builder()
                .id(1L)
                .fullName("Old Trainer")
                .build();

        TrainerEntity newTrainer = TrainerEntity.builder()
                .id(2L)
                .fullName("New Trainer")
                .build();

        TrainingProgramEntity trainingProgram =
                TrainingProgramEntity.builder()
                        .id(1L)
                        .name("Old Program")
                        .description("Old description")
                        .durationInWeeks(4)
                        .monthlyPrice(new BigDecimal("50.00"))
                        .trainer(oldTrainer)
                        .build();

        UpdateTrainingProgramRequestDto requestDto =
                new UpdateTrainingProgramRequestDto();

        requestDto.setName("Updated Program");
        requestDto.setDescription("Updated description");
        requestDto.setDurationInWeeks(12);
        requestDto.setMonthlyPrice(new BigDecimal("180.00"));
        requestDto.setTrainerId(2L);

        when(trainingProgramRepository.findById(1L))
                .thenReturn(Optional.of(trainingProgram));

        when(trainerRepository.findById(2L))
                .thenReturn(Optional.of(newTrainer));

        when(trainingProgramRepository.save(
                any(TrainingProgramEntity.class)
        )).thenAnswer(invocation -> invocation.getArgument(0));

        TrainingProgramResponseDto response =
                trainingProgramService.update(1L, requestDto);

        assertEquals("Updated Program", response.getName());
        assertEquals(12, response.getDurationInWeeks());
        assertEquals(
                new BigDecimal("180.00"),
                response.getMonthlyPrice()
        );

        verify(trainingProgramRepository)
                .save(trainingProgram);
    }

    @Test
    void delete_shouldDeleteTrainingProgram() {
        TrainingProgramEntity trainingProgram =
                TrainingProgramEntity.builder()
                        .id(1L)
                        .name("Fitness Program")
                        .build();

        when(trainingProgramRepository.findById(1L))
                .thenReturn(Optional.of(trainingProgram));

        trainingProgramService.delete(1L);

        verify(trainingProgramRepository)
                .delete(trainingProgram);
    }
}