package com.example.gymmembershipapi.controller;

import com.example.gymmembershipapi.dto.CreateMemberRequestDto;
import com.example.gymmembershipapi.dto.MemberResponseDto;
import com.example.gymmembershipapi.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MemberResponseDto create(
            @RequestBody CreateMemberRequestDto requestDto
    ) {
        return memberService.create(requestDto);
    }

    @GetMapping("/{id}")
    public MemberResponseDto getById(@PathVariable Long id) {
        return memberService.getById(id);
    }

    @GetMapping
    public List<MemberResponseDto> getAll() {
        return memberService.getAll();
    }
}