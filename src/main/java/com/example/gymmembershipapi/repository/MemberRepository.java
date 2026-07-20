package com.example.gymmembership.repository;

import com.example.gymmembershipapi.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository
        extends JpaRepository<MemberEntity, Long> {
}