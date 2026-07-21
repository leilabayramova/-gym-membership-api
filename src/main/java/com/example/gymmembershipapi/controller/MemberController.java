package com.example.gymmembershipapi.controller;

import com.example.gymmembershipapi.dto.CreateMemberRequestDto;
import com.example.gymmembershipapi.dto.MemberResponseDto;
import com.example.gymmembershipapi.dto.UpdateMemberRequestDto;
import com.example.gymmembershipapi.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
@Tag(
        name = "Member",
        description = "Member management operations"
)
public class MemberController {

    private final MemberService memberService;

    @Operation(summary = "Create a new member")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MemberResponseDto create(
            @Valid @RequestBody CreateMemberRequestDto requestDto
    ) {
        return memberService.create(requestDto);
    }

    @Operation(summary = "Get member by id")
    @GetMapping("/{id}")
    public MemberResponseDto getById(@PathVariable Long id) {
        return memberService.getById(id);
    }

    @Operation(
            summary = "Get all members",
            description = "Returns members with pagination and sorting"
    )
    @GetMapping
    public Page<MemberResponseDto> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        return memberService.getAll(page, size, sortBy, direction);
    }

    @Operation(summary = "Update member")
    @PutMapping("/{id}")
    public MemberResponseDto update(
            @PathVariable Long id,
            @Valid @RequestBody UpdateMemberRequestDto requestDto
    ) {
        return memberService.update(id, requestDto);
    }

    @Operation(summary = "Delete member")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        memberService.delete(id);
    }
}