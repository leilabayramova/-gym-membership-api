package com.example.gymmembershipapi.service;

import com.example.gymmembershipapi.dto.CreateSubscriptionRequestDto;
import com.example.gymmembershipapi.dto.SubscriptionResponseDto;
import com.example.gymmembershipapi.dto.UpdateSubscriptionRequestDto;
import com.example.gymmembershipapi.entity.MemberEntity;
import com.example.gymmembershipapi.entity.SubscriptionEntity;
import com.example.gymmembershipapi.entity.TrainingProgramEntity;
import com.example.gymmembershipapi.exception.ResourceNotFoundException;
import com.example.gymmembershipapi.repository.MemberRepository;
import com.example.gymmembershipapi.repository.SubscriptionRepository;
import com.example.gymmembershipapi.repository.TrainingProgramRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SubscriptionServiceTest {

    @Mock
    private SubscriptionRepository subscriptionRepository;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private TrainingProgramRepository trainingProgramRepository;

    @InjectMocks
    private SubscriptionService subscriptionService;

    @Test
    void create_shouldCreateSubscription() {
        CreateSubscriptionRequestDto requestDto =
                new CreateSubscriptionRequestDto();

        requestDto.setMemberId(1L);
        requestDto.setTrainingProgramId(1L);
        requestDto.setStartDate(LocalDate.of(2026, 7, 21));
        requestDto.setEndDate(LocalDate.of(2026, 9, 21));

        MemberEntity member = MemberEntity.builder()
                .id(1L)
                .fullName("Leyla Aliyeva")
                .email("leyla@example.com")
                .build();

        TrainingProgramEntity trainingProgram =
                TrainingProgramEntity.builder()
                        .id(1L)
                        .name("Fitness Program")
                        .monthlyPrice(new BigDecimal("120.00"))
                        .build();

        SubscriptionEntity savedSubscription =
                SubscriptionEntity.builder()
                        .id(1L)
                        .member(member)
                        .trainingProgram(trainingProgram)
                        .startDate(requestDto.getStartDate())
                        .endDate(requestDto.getEndDate())
                        .active(true)
                        .build();

        when(memberRepository.findById(1L))
                .thenReturn(Optional.of(member));

        when(trainingProgramRepository.findById(1L))
                .thenReturn(Optional.of(trainingProgram));

        when(subscriptionRepository.save(
                any(SubscriptionEntity.class)
        )).thenReturn(savedSubscription);

        SubscriptionResponseDto response =
                subscriptionService.create(requestDto);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals(
                LocalDate.of(2026, 7, 21),
                response.getStartDate()
        );
        assertEquals(
                LocalDate.of(2026, 9, 21),
                response.getEndDate()
        );

        verify(subscriptionRepository)
                .save(any(SubscriptionEntity.class));
    }

    @Test
    void create_shouldThrowExceptionWhenMemberDoesNotExist() {
        CreateSubscriptionRequestDto requestDto =
                new CreateSubscriptionRequestDto();

        requestDto.setMemberId(99L);
        requestDto.setTrainingProgramId(1L);

        when(memberRepository.findById(99L))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> subscriptionService.create(requestDto)
        );

        verify(subscriptionRepository, never())
                .save(any(SubscriptionEntity.class));
    }

    @Test
    void create_shouldThrowExceptionWhenProgramDoesNotExist() {
        CreateSubscriptionRequestDto requestDto =
                new CreateSubscriptionRequestDto();

        requestDto.setMemberId(1L);
        requestDto.setTrainingProgramId(99L);

        MemberEntity member = MemberEntity.builder()
                .id(1L)
                .fullName("Leyla Aliyeva")
                .build();

        when(memberRepository.findById(1L))
                .thenReturn(Optional.of(member));

        when(trainingProgramRepository.findById(99L))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> subscriptionService.create(requestDto)
        );

        verify(subscriptionRepository, never())
                .save(any(SubscriptionEntity.class));
    }

    @Test
    void getById_shouldReturnSubscription() {
        MemberEntity member = MemberEntity.builder()
                .id(1L)
                .fullName("Leyla Aliyeva")
                .build();

        TrainingProgramEntity trainingProgram =
                TrainingProgramEntity.builder()
                        .id(1L)
                        .name("Fitness Program")
                        .build();

        SubscriptionEntity subscription =
                SubscriptionEntity.builder()
                        .id(1L)
                        .member(member)
                        .trainingProgram(trainingProgram)
                        .startDate(LocalDate.of(2026, 7, 21))
                        .endDate(LocalDate.of(2026, 9, 21))
                        .active(true)
                        .build();

        when(subscriptionRepository.findById(1L))
                .thenReturn(Optional.of(subscription));

        SubscriptionResponseDto response =
                subscriptionService.getById(1L);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals(
                LocalDate.of(2026, 7, 21),
                response.getStartDate()
        );
    }

    @Test
    void getById_shouldThrowExceptionWhenSubscriptionDoesNotExist() {
        when(subscriptionRepository.findById(99L))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> subscriptionService.getById(99L)
        );
    }

    @Test
    void update_shouldUpdateSubscription() {
        MemberEntity oldMember = MemberEntity.builder()
                .id(1L)
                .fullName("Old Member")
                .build();

        MemberEntity newMember = MemberEntity.builder()
                .id(2L)
                .fullName("New Member")
                .build();

        TrainingProgramEntity oldProgram =
                TrainingProgramEntity.builder()
                        .id(1L)
                        .name("Old Program")
                        .build();

        TrainingProgramEntity newProgram =
                TrainingProgramEntity.builder()
                        .id(2L)
                        .name("New Program")
                        .build();

        SubscriptionEntity subscription =
                SubscriptionEntity.builder()
                        .id(1L)
                        .member(oldMember)
                        .trainingProgram(oldProgram)
                        .startDate(LocalDate.of(2026, 7, 1))
                        .endDate(LocalDate.of(2026, 8, 1))
                        .active(true)
                        .build();

        UpdateSubscriptionRequestDto requestDto =
                new UpdateSubscriptionRequestDto();

        requestDto.setMemberId(2L);
        requestDto.setTrainingProgramId(2L);
        requestDto.setStartDate(LocalDate.of(2026, 8, 1));
        requestDto.setEndDate(LocalDate.of(2026, 10, 1));

        when(subscriptionRepository.findById(1L))
                .thenReturn(Optional.of(subscription));

        when(memberRepository.findById(2L))
                .thenReturn(Optional.of(newMember));

        when(trainingProgramRepository.findById(2L))
                .thenReturn(Optional.of(newProgram));

        when(subscriptionRepository.save(
                any(SubscriptionEntity.class)
        )).thenAnswer(invocation -> invocation.getArgument(0));

        SubscriptionResponseDto response =
                subscriptionService.update(1L, requestDto);

        assertEquals(
                LocalDate.of(2026, 8, 1),
                response.getStartDate()
        );
        assertEquals(
                LocalDate.of(2026, 10, 1),
                response.getEndDate()
        );

        verify(subscriptionRepository)
                .save(subscription);
    }

    @Test
    void delete_shouldDeleteSubscription() {
        SubscriptionEntity subscription =
                SubscriptionEntity.builder()
                        .id(1L)
                        .active(true)
                        .build();

        when(subscriptionRepository.findById(1L))
                .thenReturn(Optional.of(subscription));

        subscriptionService.delete(1L);

        verify(subscriptionRepository)
                .delete(subscription);
    }
}