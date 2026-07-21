package com.example.gymmembershipapi.repository;

import com.example.gymmembershipapi.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository
        extends JpaRepository<MemberEntity, Long> {

    boolean existsByEmailIgnoreCase(String email);
    boolean existsByEmailIgnoreCaseAndIdNot(String email, Long id);
}