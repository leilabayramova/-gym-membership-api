package com.example.gymmembershipapi.specification;

import com.example.gymmembershipapi.entity.CategoryEntity;
import com.example.gymmembershipapi.entity.TrainingProgramEntity;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public final class TrainingProgramSpecification {

    private TrainingProgramSpecification() {
    }

    public static Specification<TrainingProgramEntity> withFilters(
            String name,
            Long trainerId,
            Long categoryId,
            BigDecimal minPrice,
            BigDecimal maxPrice,
            Integer maxDurationInWeeks
    ) {
        return (root, query, criteriaBuilder) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (name != null && !name.isBlank()) {
                predicates.add(
                        criteriaBuilder.like(
                                criteriaBuilder.lower(root.get("name")),
                                "%" + name.trim().toLowerCase(Locale.ROOT) + "%"
                        )
                );
            }

            if (trainerId != null) {
                predicates.add(
                        criteriaBuilder.equal(
                                root.get("trainer").get("id"),
                                trainerId
                        )
                );
            }

            if (categoryId != null) {
                Join<TrainingProgramEntity, CategoryEntity> categoryJoin =
                        root.join("categories", JoinType.INNER);

                predicates.add(
                        criteriaBuilder.equal(
                                categoryJoin.get("id"),
                                categoryId
                        )
                );

                query.distinct(true);
            }

            if (minPrice != null) {
                predicates.add(
                        criteriaBuilder.greaterThanOrEqualTo(
                                root.get("monthlyPrice"),
                                minPrice
                        )
                );
            }

            if (maxPrice != null) {
                predicates.add(
                        criteriaBuilder.lessThanOrEqualTo(
                                root.get("monthlyPrice"),
                                maxPrice
                        )
                );
            }

            if (maxDurationInWeeks != null) {
                predicates.add(
                        criteriaBuilder.lessThanOrEqualTo(
                                root.get("durationInWeeks"),
                                maxDurationInWeeks
                        )
                );
            }

            return criteriaBuilder.and(
                    predicates.toArray(new Predicate[0])
            );
        };
    }
}