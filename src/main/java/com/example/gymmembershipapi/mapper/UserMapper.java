package com.example.gymmembershipapi.mapper;

import com.example.gymmembershipapi.dto.UserResponseDto;
import com.example.gymmembershipapi.entity.UserEntity;

public interface UserMapper {

    static UserResponseDto toResponseDto(UserEntity user) {
        return UserResponseDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }
}