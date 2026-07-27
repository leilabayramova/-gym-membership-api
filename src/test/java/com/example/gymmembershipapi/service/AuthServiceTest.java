package com.example.gymmembershipapi.service;

import com.example.gymmembershipapi.dto.RegisterRequestDto;
import com.example.gymmembershipapi.dto.UserResponseDto;
import com.example.gymmembershipapi.entity.Role;
import com.example.gymmembershipapi.entity.UserEntity;
import com.example.gymmembershipapi.exception.DuplicateResourceException;
import com.example.gymmembershipapi.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthService authService;

    @Test
    void register_shouldEncodePasswordAndSaveUser() {
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

        UserResponseDto response = authService.register(requestDto);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("user@example.com", response.getEmail());
        assertEquals(Role.USER, response.getRole());

        verify(passwordEncoder)
                .encode("StrongPass123!");

        verify(userRepository).save(
                argThat(user ->
                        user.getEmail().equals("user@example.com")
                                && user.getPassword().equals("encoded-password")
                                && user.getRole() == Role.USER
                )
        );
    }

    @Test
    void register_shouldThrowExceptionWhenEmailAlreadyExists() {
        RegisterRequestDto requestDto = new RegisterRequestDto();
        requestDto.setEmail("user@example.com");
        requestDto.setPassword("StrongPass123!");

        when(userRepository.existsByEmailIgnoreCase("user@example.com"))
                .thenReturn(true);

        assertThrows(
                DuplicateResourceException.class,
                () -> authService.register(requestDto)
        );

        verify(passwordEncoder, never())
                .encode(anyString());

        verify(userRepository, never())
                .save(any(UserEntity.class));
    }
}