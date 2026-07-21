package com.example.gymmembershipapi.controller;

import com.example.gymmembershipapi.dto.CreateTrainingProgramRequestDto;
import com.example.gymmembershipapi.dto.TrainingProgramResponseDto;
import com.example.gymmembershipapi.dto.UpdateTrainingProgramRequestDto;
import com.example.gymmembershipapi.service.TrainingProgramService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/training-programs")
@RequiredArgsConstructor
public class TrainingProgramController {

    private final TrainingProgramService trainingProgramService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TrainingProgramResponseDto create(
           @Valid @RequestBody CreateTrainingProgramRequestDto requestDto
    ) {
        return trainingProgramService.create(requestDto);
    }

    @GetMapping("/{id}")
    public TrainingProgramResponseDto getById(@PathVariable Long id) {
        return trainingProgramService.getById(id);
    }

    @GetMapping
    public Page<TrainingProgramResponseDto> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        return trainingProgramService.getAll(
                page,
                size,
                sortBy,
                direction
        );
    }
    @PutMapping("/{id}")
    public TrainingProgramResponseDto update(
            @PathVariable Long id,
           @Valid @RequestBody UpdateTrainingProgramRequestDto requestDto
    ) {
        return trainingProgramService.update(id, requestDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        trainingProgramService.delete(id);
    }
}