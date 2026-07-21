package com.example.gymmembershipapi.controller;

import com.example.gymmembershipapi.dto.CreateSubscriptionRequestDto;
import com.example.gymmembershipapi.dto.SubscriptionResponseDto;
import com.example.gymmembershipapi.dto.UpdateSubscriptionRequestDto;
import com.example.gymmembershipapi.service.SubscriptionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/subscriptions")
@RequiredArgsConstructor
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SubscriptionResponseDto create(
            @Valid @RequestBody CreateSubscriptionRequestDto requestDto
    ) {
        return subscriptionService.create(requestDto);
    }

    @GetMapping("/{id}")
    public SubscriptionResponseDto getById(@PathVariable Long id) {
        return subscriptionService.getById(id);
    }
    @GetMapping
    public Page<SubscriptionResponseDto> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        return subscriptionService.getAll(
                page,
                size,
                sortBy,
                direction
        );
    }
    @PutMapping("/{id}")
    public SubscriptionResponseDto update(
            @PathVariable Long id,
           @Valid @RequestBody UpdateSubscriptionRequestDto requestDto
    ) {
        return subscriptionService.update(id, requestDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        subscriptionService.delete(id);
    }
}