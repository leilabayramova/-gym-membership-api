package com.example.gymmembershipapi.mapper;

import com.example.gymmembershipapi.dto.UserResponseDto;
import com.example.gymmembershipapi.entity.Role;
import com.example.gymmembershipapi.entity.UserEntity;

public interface UserMapper {

    static UserEntity toEntity(
            String email,
            String encodedPassword
    ) {
        return UserEntity.builder()
                .email(email)
                .password(encodedPassword)
                .role(Role.USER)
                .build();
    }

    static UserResponseDto toResponseDto(UserEntity user) {
        return UserResponseDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }
}