package com.example.gymmembershipapi.service;

import com.example.gymmembershipapi.dto.AuthResponseDto;
import com.example.gymmembershipapi.dto.LoginRequestDto;
import com.example.gymmembershipapi.dto.RegisterRequestDto;
import com.example.gymmembershipapi.entity.Role;
import com.example.gymmembershipapi.entity.UserEntity;
import com.example.gymmembershipapi.exception.DuplicateResourceException;
import com.example.gymmembershipapi.repository.UserRepository;
import com.example.gymmembershipapi.security.JwtService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AuthService authService;

    @Test
    void register_shouldEncodePasswordAndReturnToken() {
        RegisterRequestDto requestDto = new RegisterRequestDto();
        requestDto.setEmail(" USER@example.com ");
        requestDto.setPassword("StrongPass123!");

        when(userRepository.existsByEmailIgnoreCase("user@example.com"))
                .thenReturn(false);

        when(passwordEncoder.encode("StrongPass123!"))
                .thenReturn("encoded-password");

        when(userRepository.save(any(UserEntity.class)))
                .thenAnswer(invocation -> {
                    UserEntity user = invocation.getArgument(0);
                    user.setId(1L);
                    return user;
                });

        when(jwtService.generateToken(any(UserEntity.class)))
                .thenReturn("jwt-access-token");

        AuthResponseDto response =
                authService.register(requestDto);

        assertNotNull(response);
        assertEquals("jwt-access-token", response.getAccessToken());
        assertEquals("Bearer", response.getTokenType());
        assertEquals("user@example.com", response.getEmail());
        assertEquals(Role.USER, response.getRole());

        verify(passwordEncoder)
                .encode("StrongPass123!");

        verify(userRepository).save(
                argThat(user ->
                        user.getEmail().equals("user@example.com")
                                && user.getPassword()
                                .equals("encoded-password")
                                && user.getRole() == Role.USER
                )
        );

        verify(jwtService)
                .generateToken(any(UserEntity.class));
    }

    @Test
    void register_shouldThrowExceptionWhenEmailAlreadyExists() {
        RegisterRequestDto requestDto = new RegisterRequestDto();
        requestDto.setEmail("user@example.com");
        requestDto.setPassword("StrongPass123!");

        when(userRepository.existsByEmailIgnoreCase(
                "user@example.com"
        )).thenReturn(true);

        assertThrows(
                DuplicateResourceException.class,
                () -> authService.register(requestDto)
        );

        verify(passwordEncoder, never())
                .encode(anyString());

        verify(userRepository, never())
                .save(any(UserEntity.class));

        verify(jwtService, never())
                .generateToken(any(UserEntity.class));
    }

    @Test
    void login_shouldReturnTokenWhenCredentialsAreCorrect() {
        LoginRequestDto requestDto = new LoginRequestDto();
        requestDto.setEmail(" USER@example.com ");
        requestDto.setPassword("StrongPass123!");

        UserEntity user = UserEntity.builder()
                .id(1L)
                .email("user@example.com")
                .password("encoded-password")
                .role(Role.USER)
                .build();

        when(userRepository.findByEmailIgnoreCase(
                "user@example.com"
        )).thenReturn(Optional.of(user));

        when(passwordEncoder.matches(
                "StrongPass123!",
                "encoded-password"
        )).thenReturn(true);

        when(jwtService.generateToken(user))
                .thenReturn("jwt-access-token");

        AuthResponseDto response =
                authService.login(requestDto);

        assertNotNull(response);
        assertEquals("jwt-access-token", response.getAccessToken());
        assertEquals("Bearer", response.getTokenType());
        assertEquals("user@example.com", response.getEmail());
        assertEquals(Role.USER, response.getRole());

        verify(passwordEncoder).matches(
                "StrongPass123!",
                "encoded-password"
        );

        verify(jwtService)
                .generateToken(user);
    }

    @Test
    void login_shouldThrowExceptionWhenEmailDoesNotExist() {
        LoginRequestDto requestDto = new LoginRequestDto();
        requestDto.setEmail("unknown@example.com");
        requestDto.setPassword("StrongPass123!");

        when(userRepository.findByEmailIgnoreCase(
                "unknown@example.com"
        )).thenReturn(Optional.empty());

        BadCredentialsException exception = assertThrows(
                BadCredentialsException.class,
                () -> authService.login(requestDto)
        );

        assertEquals(
                "Invalid email or password",
                exception.getMessage()
        );

        verify(passwordEncoder, never())
                .matches(anyString(), anyString());

        verify(jwtService, never())
                .generateToken(any(UserEntity.class));
    }

    @Test
    void login_shouldThrowExceptionWhenPasswordIsIncorrect() {
        LoginRequestDto requestDto = new LoginRequestDto();
        requestDto.setEmail("user@example.com");
        requestDto.setPassword("WrongPassword123!");

        UserEntity user = UserEntity.builder()
                .id(1L)
                .email("user@example.com")
                .password("encoded-password")
                .role(Role.USER)
                .build();

        when(userRepository.findByEmailIgnoreCase(
                "user@example.com"
        )).thenReturn(Optional.of(user));

        when(passwordEncoder.matches(
                "WrongPassword123!",
                "encoded-password"
        )).thenReturn(false);

        BadCredentialsException exception = assertThrows(
                BadCredentialsException.class,
                () -> authService.login(requestDto)
        );

        assertEquals(
                "Invalid email or password",
                exception.getMessage()
        );

        verify(jwtService, never())
                .generateToken(any(UserEntity.class));
    }
}