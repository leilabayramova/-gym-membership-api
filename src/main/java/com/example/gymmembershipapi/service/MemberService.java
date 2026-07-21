package com.example.gymmembershipapi.service;

import com.example.gymmembershipapi.dto.CreateMemberRequestDto;
import com.example.gymmembershipapi.dto.MemberResponseDto;
import com.example.gymmembershipapi.entity.MemberEntity;
import com.example.gymmembershipapi.mapper.MemberMapper;
import com.example.gymmembershipapi.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberResponseDto create(CreateMemberRequestDto requestDto) {
        MemberEntity member = MemberMapper.toEntity(requestDto);
        MemberEntity savedMember = memberRepository.save(member);

        return MemberMapper.toResponseDto(savedMember);
    }

    public MemberResponseDto getById(Long id) {
        MemberEntity member = memberRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Member not found with id: " + id)
                );

        return MemberMapper.toResponseDto(member);
    }

    public List<MemberResponseDto> getAll() {
        return memberRepository.findAll()
                .stream()
                .map(MemberMapper::toResponseDto)
                .toList();
    }
}