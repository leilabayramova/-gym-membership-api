package com.example.gymmembershipapi.service;

import com.example.gymmembershipapi.dto.CreateEnrollmentRequestDto;
import com.example.gymmembershipapi.dto.CreateMemberRequestDto;
import com.example.gymmembershipapi.entity.TrainerEntity;
import com.example.gymmembershipapi.entity.TrainingProgramEntity;
import com.example.gymmembershipapi.repository.MemberRepository;
import com.example.gymmembershipapi.repository.SubscriptionRepository;
import com.example.gymmembershipapi.repository.TrainerRepository;
import com.example.gymmembershipapi.repository.TrainingProgramRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("test")
class EnrollmentTransactionIntegrationTest {

    @Autowired
    private EnrollmentService enrollmentService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private TrainingProgramRepository trainingProgramRepository;

    @Autowired
    private TrainerRepository trainerRepository;

    private TrainingProgramEntity trainingProgram;

    @BeforeEach
    void setUp() {
        subscriptionRepository.deleteAll();
        memberRepository.deleteAll();
        trainingProgramRepository.deleteAll();
        trainerRepository.deleteAll();

        TrainerEntity trainer = trainerRepository.save(
                TrainerEntity.builder()
                        .fullName("Test Trainer")
                        .email("trainer@test.com")
                        .phoneNumber("+994501112233")
                        .specialization("Fitness")
                        .build()
        );

        trainingProgram = trainingProgramRepository.save(
                TrainingProgramEntity.builder()
                        .name("Test Program")
                        .description("Program used for rollback testing")
                        .durationInWeeks(8)
                        .monthlyPrice(new BigDecimal("100.00"))
                        .trainer(trainer)
                        .build()
        );
    }

    @Test
    void enroll_shouldRollbackMemberWhenSubscriptionCreationFails() {
        CreateMemberRequestDto memberRequest =
                new CreateMemberRequestDto();

        memberRequest.setFullName("Rollback Member");
        memberRequest.setEmail("rollback.member@example.com");
        memberRequest.setPhoneNumber("+994501234567");

        CreateEnrollmentRequestDto requestDto =
                new CreateEnrollmentRequestDto();

        requestDto.setMember(memberRequest);

        requestDto.setStartDate(null);

        requestDto.setEndDate(LocalDate.now().plusMonths(2));
        requestDto.setTrainingProgramId(trainingProgram.getId());

        long memberCountBefore = memberRepository.count();
        long subscriptionCountBefore = subscriptionRepository.count();

        assertThrows(
                RuntimeException.class,
                () -> enrollmentService.enroll(requestDto)
        );

        assertEquals(
                memberCountBefore,
                memberRepository.count()
        );

        assertEquals(
                subscriptionCountBefore,
                subscriptionRepository.count()
        );
    }
}