package com.example.gymmembershipapi.service;

import com.example.gymmembershipapi.dto.CreateTrainerRequestDto;
import com.example.gymmembershipapi.dto.TrainerResponseDto;
import com.example.gymmembershipapi.dto.UpdateTrainerRequestDto;
import com.example.gymmembershipapi.entity.TrainerEntity;
import com.example.gymmembershipapi.exception.DuplicateResourceException;
import com.example.gymmembershipapi.exception.ResourceNotFoundException;
import com.example.gymmembershipapi.repository.TrainerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TrainerServiceTest {

    @Mock
    private TrainerRepository trainerRepository;

    @InjectMocks
    private TrainerService trainerService;

    @Test
    void create_shouldCreateTrainer() {
        CreateTrainerRequestDto requestDto =
                new CreateTrainerRequestDto();

        requestDto.setFullName("Leyla Aliyeva");
        requestDto.setEmail("leyla@example.com");
        requestDto.setPhoneNumber("0504444444");
        requestDto.setSpecialization("Fitness");

        TrainerEntity savedTrainer = TrainerEntity.builder()
                .id(1L)
                .fullName("Leyla Aliyeva")
                .email("leyla@example.com")
                .phoneNumber("0504444444")
                .specialization("Fitness")
                .build();

        when(trainerRepository.existsByEmailIgnoreCase(
                requestDto.getEmail()
        )).thenReturn(false);

        when(trainerRepository.save(any(TrainerEntity.class)))
                .thenReturn(savedTrainer);

        TrainerResponseDto response =
                trainerService.create(requestDto);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Leyla Aliyeva", response.getFullName());
        assertEquals("leyla@example.com", response.getEmail());

        verify(trainerRepository).save(any(TrainerEntity.class));
    }

    @Test
    void create_shouldThrowExceptionWhenEmailAlreadyExists() {
        CreateTrainerRequestDto requestDto =
                new CreateTrainerRequestDto();

        requestDto.setEmail("leyla@example.com");

        when(trainerRepository.existsByEmailIgnoreCase(
                requestDto.getEmail()
        )).thenReturn(true);

        assertThrows(
                DuplicateResourceException.class,
                () -> trainerService.create(requestDto)
        );

        verify(trainerRepository, never())
                .save(any(TrainerEntity.class));
    }

    @Test
    void getById_shouldReturnTrainer() {
        TrainerEntity trainer = TrainerEntity.builder()
                .id(1L)
                .fullName("Leyla Aliyeva")
                .email("leyla@example.com")
                .phoneNumber("0504444444")
                .specialization("Fitness")
                .build();

        when(trainerRepository.findById(1L))
                .thenReturn(Optional.of(trainer));

        TrainerResponseDto response =
                trainerService.getById(1L);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Leyla Aliyeva", response.getFullName());
    }

    @Test
    void getById_shouldThrowExceptionWhenTrainerDoesNotExist() {
        when(trainerRepository.findById(99L))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> trainerService.getById(99L)
        );
    }

    @Test
    void update_shouldUpdateTrainer() {
        TrainerEntity trainer = TrainerEntity.builder()
                .id(1L)
                .fullName("Old Name")
                .email("old@example.com")
                .phoneNumber("0501111111")
                .specialization("Fitness")
                .build();

        UpdateTrainerRequestDto requestDto =
                new UpdateTrainerRequestDto();

        requestDto.setFullName("New Name");
        requestDto.setEmail("new@example.com");
        requestDto.setPhoneNumber("0502222222");
        requestDto.setSpecialization("CrossFit");

        when(trainerRepository.findById(1L))
                .thenReturn(Optional.of(trainer));

        when(trainerRepository.existsByEmailIgnoreCaseAndIdNot(
                requestDto.getEmail(),
                1L
        )).thenReturn(false);

        when(trainerRepository.save(any(TrainerEntity.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        TrainerResponseDto response =
                trainerService.update(1L, requestDto);

        assertEquals("New Name", response.getFullName());
        assertEquals("new@example.com", response.getEmail());
        assertEquals("CrossFit", response.getSpecialization());

        verify(trainerRepository).save(trainer);
    }

    @Test
    void delete_shouldDeleteTrainer() {
        TrainerEntity trainer = TrainerEntity.builder()
                .id(1L)
                .fullName("Leyla Aliyeva")
                .email("leyla@example.com")
                .build();

        when(trainerRepository.findById(1L))
                .thenReturn(Optional.of(trainer));

        trainerService.delete(1L);

        verify(trainerRepository).delete(trainer);
    }
}