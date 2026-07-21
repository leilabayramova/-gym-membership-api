package com.example.gymmembershipapi.service;

import com.example.gymmembershipapi.dto.CreateSubscriptionRequestDto;
import com.example.gymmembershipapi.dto.SubscriptionResponseDto;
import com.example.gymmembershipapi.dto.UpdateSubscriptionRequestDto;
import com.example.gymmembershipapi.entity.MemberEntity;
import com.example.gymmembershipapi.entity.SubscriptionEntity;
import com.example.gymmembershipapi.entity.TrainingProgramEntity;
import com.example.gymmembershipapi.mapper.SubscriptionMapper;
import com.example.gymmembershipapi.repository.MemberRepository;
import com.example.gymmembershipapi.repository.SubscriptionRepository;
import com.example.gymmembershipapi.repository.TrainingProgramRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final MemberRepository memberRepository;
    private final TrainingProgramRepository trainingProgramRepository;

    public SubscriptionResponseDto create(
            CreateSubscriptionRequestDto requestDto
    ) {
        MemberEntity member = memberRepository
                .findById(requestDto.getMemberId())
                .orElseThrow(() -> new RuntimeException(
                        "Member not found with id: " + requestDto.getMemberId()
                ));

        TrainingProgramEntity trainingProgram = trainingProgramRepository
                .findById(requestDto.getTrainingProgramId())
                .orElseThrow(() -> new RuntimeException(
                        "Training program not found with id: "
                                + requestDto.getTrainingProgramId()
                ));

        SubscriptionEntity subscription = SubscriptionMapper.toEntity(
                requestDto,
                member,
                trainingProgram
        );

        SubscriptionEntity savedSubscription =
                subscriptionRepository.save(subscription);

        return SubscriptionMapper.toResponseDto(savedSubscription);
    }

    public SubscriptionResponseDto getById(Long id) {
        SubscriptionEntity subscription = subscriptionRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Subscription not found with id: " + id
                ));

        return SubscriptionMapper.toResponseDto(subscription);
    }

    public List<SubscriptionResponseDto> getAll() {
        return subscriptionRepository.findAll()
                .stream()
                .map(SubscriptionMapper::toResponseDto)
                .toList();
    }
    public SubscriptionResponseDto update(
            Long id,
            UpdateSubscriptionRequestDto requestDto
    ) {
        SubscriptionEntity subscription =
                subscriptionRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException("Subscription not found")
                        );

        MemberEntity member = memberRepository
                .findById(requestDto.getMemberId())
                .orElseThrow(() ->
                        new RuntimeException("Member not found")
                );

        TrainingProgramEntity trainingProgram =
                trainingProgramRepository
                        .findById(requestDto.getTrainingProgramId())
                        .orElseThrow(() ->
                                new RuntimeException("Training program not found")
                        );

        SubscriptionMapper.updateEntity(
                subscription,
                requestDto,
                member,
                trainingProgram
        );

        SubscriptionEntity updatedSubscription =
                subscriptionRepository.save(subscription);

        return SubscriptionMapper.toResponseDto(updatedSubscription);
    }

    public void delete(Long id) {
        SubscriptionEntity subscription =
                subscriptionRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException("Subscription not found")
                        );

        subscriptionRepository.delete(subscription);
    }
}