package com.example.gymmembershipapi.controller;

import com.example.gymmembershipapi.dto.CreateTrainerRequestDto;
import com.example.gymmembershipapi.dto.TrainerResponseDto;
import com.example.gymmembershipapi.service.TrainerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trainers")
@RequiredArgsConstructor
public class TrainerController {

    private final TrainerService trainerService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TrainerResponseDto create(
            @RequestBody CreateTrainerRequestDto requestDto
    ) {
        return trainerService.create(requestDto);
    }

    @GetMapping("/{id}")
    public TrainerResponseDto getById(@PathVariable Long id) {
        return trainerService.getById(id);
    }

    @GetMapping
    public List<TrainerResponseDto> getAll() {
        return trainerService.getAll();
    }
}