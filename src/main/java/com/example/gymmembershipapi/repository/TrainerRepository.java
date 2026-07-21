package com.example.gymmembershipapi.repository;

import com.example.gymmembershipapi.entity.TrainerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainerRepository
        extends JpaRepository<TrainerEntity, Long> {

    boolean existsByEmailIgnoreCase(String email);
}
