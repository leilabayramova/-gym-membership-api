package com.example.gymmembershipapi.service;

import com.example.gymmembershipapi.dto.CreateEnrollmentRequestDto;
import com.example.gymmembershipapi.dto.EnrollmentResponseDto;
import com.example.gymmembershipapi.entity.MemberEntity;
import com.example.gymmembershipapi.entity.SubscriptionEntity;
import com.example.gymmembershipapi.entity.TrainingProgramEntity;
import com.example.gymmembershipapi.exception.DuplicateResourceException;
import com.example.gymmembershipapi.exception.ResourceNotFoundException;
import com.example.gymmembershipapi.mapper.EnrollmentMapper;
import com.example.gymmembershipapi.mapper.MemberMapper;
import com.example.gymmembershipapi.mapper.SubscriptionMapper;
import com.example.gymmembershipapi.repository.MemberRepository;
import com.example.gymmembershipapi.repository.SubscriptionRepository;
import com.example.gymmembershipapi.repository.TrainingProgramRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;

@Service
@RequiredArgsConstructor
public class EnrollmentService {

    private final MemberRepository memberRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final TrainingProgramRepository trainingProgramRepository;
    private final NotificationService notificationService;

    @Transactional
    public EnrollmentResponseDto enroll(CreateEnrollmentRequestDto requestDto) {

        String normalizedEmail =
                requestDto.getMember().getEmail().trim().toLowerCase();

        if (memberRepository.existsByEmailIgnoreCase(normalizedEmail)) {
            throw new DuplicateResourceException(
                    "Member already exists with email: " + normalizedEmail
            );
        }

        TrainingProgramEntity trainingProgram =
                trainingProgramRepository.findById(
                        requestDto.getTrainingProgramId()
                ).orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Training program not found with id: "
                                        + requestDto.getTrainingProgramId()
                        )
                );

        requestDto.getMember().setEmail(normalizedEmail);

        MemberEntity member =
                MemberMapper.toEntity(requestDto.getMember());

        MemberEntity savedMember =
                memberRepository.save(member);

        SubscriptionEntity subscription =
                SubscriptionMapper.toEntity(
                        requestDto,
                        savedMember,
                        trainingProgram
                );

        SubscriptionEntity savedSubscription =
                subscriptionRepository.save(subscription);

        notificationService.sendEnrollmentNotification(
                savedMember.getEmail(),
                trainingProgram.getName()
        );

        return EnrollmentMapper.toResponseDto(
                savedMember,
                savedSubscription
        );
    }
}