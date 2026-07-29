package com.example.gymmembershipapi.security;

import com.example.gymmembershipapi.entity.Role;
import com.example.gymmembershipapi.entity.UserEntity;
import io.jsonwebtoken.ExpiredJwtException;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JwtServiceTest {

    private static final String SECRET =
            "MDEyMzQ1Njc4OWFiY2RlZjAxMjM0NTY3ODlhYmNkZWY=";

    @Test
    void shouldGenerateValidToken() {
        JwtService jwtService = new JwtService(SECRET, 3600000);

        UserEntity user = UserEntity.builder()
                .email("user@example.com")
                .password("encodedPassword")
                .role(Role.USER)
                .build();

        String token = jwtService.generateToken(user);

        UserDetails userDetails = User.withUsername(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRole().name())
                .build();

        assertEquals("user@example.com", jwtService.extractEmail(token));
        assertEquals("USER", jwtService.extractRole(token));
        assertTrue(jwtService.isTokenValid(token, userDetails));
    }

    @Test
    void shouldRejectExpiredToken() {
        JwtService jwtService = new JwtService(SECRET, -1000);

        UserEntity user = UserEntity.builder()
                .email("user@example.com")
                .password("encodedPassword")
                .role(Role.USER)
                .build();

        String token = jwtService.generateToken(user);

        assertThrows(
                ExpiredJwtException.class,
                () -> jwtService.extractEmail(token)
        );
    }
}