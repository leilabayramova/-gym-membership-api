package com.example.gymmembershipapi.controller;

import com.example.gymmembershipapi.dto.CreateEnrollmentRequestDto;
import com.example.gymmembershipapi.dto.EnrollmentResponseDto;
import com.example.gymmembershipapi.service.EnrollmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/enrollments")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@Tag(
        name = "Enrollment",
        description = "Member registration and initial subscription operations"
)
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    @Operation(
            summary = "Create member with subscription",
            description = "Creates a new member and initial subscription in one transaction"
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EnrollmentResponseDto enroll(
            @Valid @RequestBody CreateEnrollmentRequestDto requestDto
    ) {
        return enrollmentService.enroll(requestDto);
    }
}