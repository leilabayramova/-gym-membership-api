package com.example.gymmembershipapi.repository;

import com.example.gymmembershipapi.entity.SubscriptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface SubscriptionRepository
        extends JpaRepository<SubscriptionEntity, Long> {
    List<SubscriptionEntity> findAllByActiveTrueAndEndDateBefore(LocalDate date);
}