package com.example.gymmembershipapi.service;

import com.example.gymmembershipapi.dto.CreateMemberRequestDto;
import com.example.gymmembershipapi.dto.MemberResponseDto;
import com.example.gymmembershipapi.dto.UpdateMemberRequestDto;
import com.example.gymmembershipapi.entity.MemberEntity;
import com.example.gymmembershipapi.exception.DuplicateResourceException;
import com.example.gymmembershipapi.exception.ResourceNotFoundException;
import com.example.gymmembershipapi.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private MemberService memberService;

    @Test
    void create_shouldCreateMember() {
        CreateMemberRequestDto requestDto =
                new CreateMemberRequestDto();

        requestDto.setFullName("Leyla Aliyeva");
        requestDto.setEmail("leyla@example.com");
        requestDto.setPhoneNumber("0504444444");

        MemberEntity savedMember = MemberEntity.builder()
                .id(1L)
                .fullName("Leyla Aliyeva")
                .email("leyla@example.com")
                .phoneNumber("0504444444")
                .registrationDate(LocalDate.now())
                .build();

        when(memberRepository.existsByEmailIgnoreCase(
                requestDto.getEmail()
        )).thenReturn(false);

        when(memberRepository.save(any(MemberEntity.class)))
                .thenReturn(savedMember);

        MemberResponseDto response =
                memberService.create(requestDto);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Leyla Aliyeva", response.getFullName());
        assertEquals("leyla@example.com", response.getEmail());

        verify(memberRepository)
                .save(any(MemberEntity.class));
    }

    @Test
    void create_shouldThrowExceptionWhenEmailAlreadyExists() {
        CreateMemberRequestDto requestDto =
                new CreateMemberRequestDto();

        requestDto.setEmail("leyla@example.com");

        when(memberRepository.existsByEmailIgnoreCase(
                requestDto.getEmail()
        )).thenReturn(true);

        assertThrows(
                DuplicateResourceException.class,
                () -> memberService.create(requestDto)
        );

        verify(memberRepository, never())
                .save(any(MemberEntity.class));
    }

    @Test
    void getById_shouldReturnMember() {
        MemberEntity member = MemberEntity.builder()
                .id(1L)
                .fullName("Leyla Aliyeva")
                .email("leyla@example.com")
                .phoneNumber("0504444444")
                .registrationDate(LocalDate.now())
                .build();

        when(memberRepository.findById(1L))
                .thenReturn(Optional.of(member));

        MemberResponseDto response =
                memberService.getById(1L);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Leyla Aliyeva", response.getFullName());
        assertEquals("leyla@example.com", response.getEmail());
    }

    @Test
    void getById_shouldThrowExceptionWhenMemberDoesNotExist() {
        when(memberRepository.findById(99L))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> memberService.getById(99L)
        );
    }

    @Test
    void update_shouldUpdateMember() {
        MemberEntity member = MemberEntity.builder()
                .id(1L)
                .fullName("Old Name")
                .email("old@example.com")
                .phoneNumber("0501111111")
                .registrationDate(LocalDate.now())
                .build();

        UpdateMemberRequestDto requestDto =
                new UpdateMemberRequestDto();

        requestDto.setFullName("New Name");
        requestDto.setEmail("new@example.com");
        requestDto.setPhoneNumber("0502222222");

        when(memberRepository.findById(1L))
                .thenReturn(Optional.of(member));

        when(memberRepository.existsByEmailIgnoreCaseAndIdNot(
                requestDto.getEmail(),
                1L
        )).thenReturn(false);

        when(memberRepository.save(any(MemberEntity.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        MemberResponseDto response =
                memberService.update(1L, requestDto);

        assertEquals("New Name", response.getFullName());
        assertEquals("new@example.com", response.getEmail());
        assertEquals("0502222222", response.getPhoneNumber());

        verify(memberRepository).save(member);
    }

    @Test
    void delete_shouldDeleteMember() {
        MemberEntity member = MemberEntity.builder()
                .id(1L)
                .fullName("Leyla Aliyeva")
                .email("leyla@example.com")
                .build();

        when(memberRepository.findById(1L))
                .thenReturn(Optional.of(member));

        memberService.delete(1L);

        verify(memberRepository).delete(member);
    }
}