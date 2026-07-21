package com.example.gymmembershipapi.controller;

import com.example.gymmembershipapi.dto.CreateTrainingProgramRequestDto;
import com.example.gymmembershipapi.dto.TrainingProgramResponseDto;
import com.example.gymmembershipapi.service.TrainingProgramService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/training-programs")
@RequiredArgsConstructor
public class TrainingProgramController {

    private final TrainingProgramService trainingProgramService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TrainingProgramResponseDto create(
            @RequestBody CreateTrainingProgramRequestDto requestDto
    ) {
        return trainingProgramService.create(requestDto);
    }

    @GetMapping("/{id}")
    public TrainingProgramResponseDto getById(@PathVariable Long id) {
        return trainingProgramService.getById(id);
    }

    @GetMapping
    public List<TrainingProgramResponseDto> getAll() {
        return trainingProgramService.getAll();
    }
}