package com.example.gymmembershipapi.repository;

import com.example.gymmembershipapi.entity.SubscriptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepository
        extends JpaRepository<SubscriptionEntity, Long> {
}