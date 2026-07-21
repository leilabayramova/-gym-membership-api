package com.example.gymmembershipapi.controller;

import com.example.gymmembershipapi.dto.CreateSubscriptionRequestDto;
import com.example.gymmembershipapi.dto.SubscriptionResponseDto;
import com.example.gymmembershipapi.dto.UpdateSubscriptionRequestDto;
import com.example.gymmembershipapi.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subscriptions")
@RequiredArgsConstructor
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SubscriptionResponseDto create(
            @RequestBody CreateSubscriptionRequestDto requestDto
    ) {
        return subscriptionService.create(requestDto);
    }

    @GetMapping("/{id}")
    public SubscriptionResponseDto getById(@PathVariable Long id) {
        return subscriptionService.getById(id);
    }

    @GetMapping
    public List<SubscriptionResponseDto> getAll() {
        return subscriptionService.getAll();
    }
    @PutMapping("/{id}")
    public SubscriptionResponseDto update(
            @PathVariable Long id,
            @RequestBody UpdateSubscriptionRequestDto requestDto
    ) {
        return subscriptionService.update(id, requestDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        subscriptionService.delete(id);
    }
}