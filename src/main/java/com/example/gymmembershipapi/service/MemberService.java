package com.example.gymmembershipapi.service;

import com.example.gymmembershipapi.dto.CreateMemberRequestDto;
import com.example.gymmembershipapi.dto.MemberResponseDto;
import com.example.gymmembershipapi.dto.UpdateMemberRequestDto;
import com.example.gymmembershipapi.entity.MemberEntity;
import com.example.gymmembershipapi.exception.DuplicateResourceException;
import com.example.gymmembershipapi.exception.ResourceNotFoundException;
import com.example.gymmembershipapi.mapper.MemberMapper;
import com.example.gymmembershipapi.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberResponseDto create(CreateMemberRequestDto requestDto) {

        if (memberRepository.existsByEmailIgnoreCase(requestDto.getEmail())) {
            throw new DuplicateResourceException(
                    "Member with this email already exists"
            );
        }

        MemberEntity member = MemberMapper.toEntity(requestDto);
        MemberEntity savedMember = memberRepository.save(member);

        return MemberMapper.toResponseDto(savedMember);
    }

    public MemberResponseDto getById(Long id) {
        MemberEntity member = memberRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Member not found with id: " + id
                        )
                );

        return MemberMapper.toResponseDto(member);
    }

    public Page<MemberResponseDto> getAll(
            int page,
            int size,
            String sortBy,
            String direction
    ) {
        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return memberRepository.findAll(pageable)
                .map(MemberMapper::toResponseDto);
    }

    public MemberResponseDto update(
            Long id,
            UpdateMemberRequestDto requestDto
    ) {
        MemberEntity member = memberRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Member not found with id: " + id
                        )
                );

        if (memberRepository.existsByEmailIgnoreCaseAndIdNot(
                requestDto.getEmail(),
                id
        )) {
            throw new DuplicateResourceException(
                    "Member with this email already exists"
            );
        }

        MemberMapper.updateEntity(member, requestDto);

        MemberEntity updatedMember = memberRepository.save(member);

        return MemberMapper.toResponseDto(updatedMember);
    }

    public void delete(Long id) {
        MemberEntity member = memberRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Member not found with id: " + id
                        )
                );

        memberRepository.delete(member);
    }
}