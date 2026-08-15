package com.example.gymmembershipapi.scheduler;

import com.example.gymmembershipapi.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class SubscriptionScheduler {

    private final SubscriptionService subscriptionService;

    @Scheduled(cron = "0 0 0 * * *")
    public void deactivateExpiredSubscriptions() {
        int deactivatedCount =
                subscriptionService.deactivateExpiredSubscriptions();

        log.info(
                "{} expired subscriptions deactivated",
                deactivatedCount
        );
    }
}