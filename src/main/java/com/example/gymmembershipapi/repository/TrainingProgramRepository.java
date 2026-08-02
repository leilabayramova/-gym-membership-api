package com.example.gymmembershipapi.repository;

import com.example.gymmembershipapi.entity.TrainingProgramEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface TrainingProgramRepository
        extends JpaRepository<TrainingProgramEntity, Long> {

    boolean existsByTrainerId(Long trainerId);

    @Query("""
            SELECT DISTINCT trainingProgram
            FROM TrainingProgramEntity trainingProgram
            JOIN trainingProgram.categories category
            WHERE trainingProgram.trainer.id = :trainerId
              AND category.id = :categoryId
              AND trainingProgram.monthlyPrice BETWEEN :minPrice AND :maxPrice
              AND trainingProgram.durationInWeeks <= :maxDurationInWeeks
            """)
    List<TrainingProgramEntity> findByComplexFilter(
            @Param("trainerId") Long trainerId,
            @Param("categoryId") Long categoryId,
            @Param("minPrice") BigDecimal minPrice,
            @Param("maxPrice") BigDecimal maxPrice,
            @Param("maxDurationInWeeks") Integer maxDurationInWeeks
    );
}