package com.example.gymmembershipapi.controller;

import com.example.gymmembershipapi.dto.CreateTrainingProgramRequestDto;
import com.example.gymmembershipapi.dto.TrainingProgramResponseDto;
import com.example.gymmembershipapi.dto.UpdateTrainingProgramRequestDto;
import com.example.gymmembershipapi.service.TrainingProgramService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/training-programs")
@RequiredArgsConstructor
@Tag(
        name = "Training Program",
        description = "Training program management operations"
)
public class TrainingProgramController {

    private final TrainingProgramService trainingProgramService;

    @Operation(summary = "Create a new training program")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TrainingProgramResponseDto create(
            @Valid @RequestBody
            CreateTrainingProgramRequestDto requestDto
    ) {
        return trainingProgramService.create(requestDto);
    }

    @Operation(summary = "Get training program by id")
    @GetMapping("/{id}")
    public TrainingProgramResponseDto getById(
            @PathVariable Long id
    ) {
        return trainingProgramService.getById(id);
    }

    @Operation(
            summary = "Get all training programs",
            description = "Returns training programs with pagination and sorting"
    )
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

    @Operation(summary = "Update training program")
    @PutMapping("/{id}")
    public TrainingProgramResponseDto update(
            @PathVariable Long id,
            @Valid @RequestBody
            UpdateTrainingProgramRequestDto requestDto
    ) {
        return trainingProgramService.update(id, requestDto);
    }

    @Operation(summary = "Delete training program")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        trainingProgramService.delete(id);
    }
}