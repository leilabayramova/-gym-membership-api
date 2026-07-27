package com.example.gymmembershipapi.service;

import com.example.gymmembershipapi.dto.AuthResponseDto;
import com.example.gymmembershipapi.dto.LoginRequestDto;
import com.example.gymmembershipapi.dto.RegisterRequestDto;
import com.example.gymmembershipapi.entity.UserEntity;
import com.example.gymmembershipapi.exception.DuplicateResourceException;
import com.example.gymmembershipapi.mapper.UserMapper;
import com.example.gymmembershipapi.repository.UserRepository;
import com.example.gymmembershipapi.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthResponseDto register(RegisterRequestDto requestDto) {
        String normalizedEmail = normalizeEmail(requestDto.getEmail());

        if (userRepository.existsByEmailIgnoreCase(normalizedEmail)) {
            throw new DuplicateResourceException(
                    "User already exists with email: " + normalizedEmail
            );
        }

        String encodedPassword = passwordEncoder.encode(
                requestDto.getPassword()
        );

        UserEntity user = UserMapper.toEntity(
                normalizedEmail,
                encodedPassword
        );

        UserEntity savedUser = userRepository.save(user);

        String accessToken = jwtService.generateToken(savedUser);

        return toAuthResponse(savedUser, accessToken);
    }

    public AuthResponseDto login(LoginRequestDto requestDto) {
        String normalizedEmail = normalizeEmail(requestDto.getEmail());

        UserEntity user = userRepository
                .findByEmailIgnoreCase(normalizedEmail)
                .orElseThrow(() ->
                        new BadCredentialsException(
                                "Invalid email or password"
                        )
                );

        boolean passwordMatches = passwordEncoder.matches(
                requestDto.getPassword(),
                user.getPassword()
        );

        if (!passwordMatches) {
            throw new BadCredentialsException(
                    "Invalid email or password"
            );
        }

        String accessToken = jwtService.generateToken(user);

        return toAuthResponse(user, accessToken);
    }

    private AuthResponseDto toAuthResponse(
            UserEntity user,
            String accessToken
    ) {
        return AuthResponseDto.builder()
                .accessToken(accessToken)
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }

    private String normalizeEmail(String email) {
        return email
                .trim()
                .toLowerCase(Locale.ROOT);
    }
}