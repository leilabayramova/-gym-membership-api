package com.example.gymmembershipapi.controller;

import com.example.gymmembershipapi.dto.CreateMemberRequestDto;
import com.example.gymmembershipapi.dto.MemberResponseDto;
import com.example.gymmembershipapi.dto.UpdateMemberRequestDto;
import com.example.gymmembershipapi.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MemberResponseDto create(
            @Valid @RequestBody CreateMemberRequestDto requestDto
    ) {
        return memberService.create(requestDto);
    }

    @GetMapping("/{id}")
    public MemberResponseDto getById(@PathVariable Long id) {
        return memberService.getById(id);
    }

    @GetMapping
    public Page<MemberResponseDto> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        return memberService.getAll(page, size, sortBy, direction);
    }
    @PutMapping("/{id}")
    public MemberResponseDto update(
            @PathVariable Long id,
            @Valid @RequestBody UpdateMemberRequestDto requestDto
    ) {
        return memberService.update(id, requestDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        memberService.delete(id);
    }
}