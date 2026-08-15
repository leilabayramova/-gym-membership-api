package com.example.gymmembershipapi.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class NotificationService {

    @Async
    public void sendEnrollmentNotification(
            String email,
            String trainingProgramName
    ) {
        log.info(
                "Sending enrollment notification to {} for training program {}",
                email,
                trainingProgramName
        );

        try {
            Thread.sleep(3000);
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
        }

        log.info(
                "Enrollment notification successfully sent to {}",
                email
        );
    }
}