package com.example.gymmembership.repository;

import com.example.gymmembershipapi.entity.TrainingProgramEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainingProgramRepository
        extends JpaRepository<TrainingProgramEntity, Long> {
}