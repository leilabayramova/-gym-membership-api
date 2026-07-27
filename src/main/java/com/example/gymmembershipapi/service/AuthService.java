package com.example.gymmembershipapi.service;

import com.example.gymmembershipapi.dto.RegisterRequestDto;
import com.example.gymmembershipapi.dto.UserResponseDto;
import com.example.gymmembershipapi.entity.Role;
import com.example.gymmembershipapi.entity.UserEntity;
import com.example.gymmembershipapi.exception.DuplicateResourceException;
import com.example.gymmembershipapi.mapper.UserMapper;
import com.example.gymmembershipapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserResponseDto register(RegisterRequestDto requestDto) {
        String normalizedEmail = requestDto.getEmail()
                .trim()
                .toLowerCase(Locale.ROOT);

        if (userRepository.existsByEmailIgnoreCase(normalizedEmail)) {
            throw new DuplicateResourceException(
                    "User already exists with email: " + normalizedEmail
            );
        }

        UserEntity user = UserEntity.builder()
                .email(normalizedEmail)
                .password(
                        passwordEncoder.encode(
                                requestDto.getPassword()
                        )
                )
                .role(Role.USER)
                .build();

        UserEntity savedUser = userRepository.save(user);

        return UserMapper.toResponseDto(savedUser);
    }
}