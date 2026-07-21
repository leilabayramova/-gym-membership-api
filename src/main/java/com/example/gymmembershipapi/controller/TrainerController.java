package com.example.gymmembershipapi.controller;

import com.example.gymmembershipapi.dto.CreateTrainerRequestDto;
import com.example.gymmembershipapi.dto.TrainerResponseDto;
import com.example.gymmembershipapi.dto.UpdateTrainerRequestDto;
import com.example.gymmembershipapi.service.TrainerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/trainers")
@RequiredArgsConstructor
@Tag(
        name = "Trainer",
        description = "Trainer management operations"
)
public class TrainerController {

    private final TrainerService trainerService;

    @Operation(summary = "Create a new trainer")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TrainerResponseDto create(
            @Valid @RequestBody CreateTrainerRequestDto requestDto
    ) {
        return trainerService.create(requestDto);
    }

    @Operation(summary = "Get trainer by id")
    @GetMapping("/{id}")
    public TrainerResponseDto getById(@PathVariable Long id) {
        return trainerService.getById(id);
    }

    @Operation(
            summary = "Get all trainers",
            description = "Returns trainers with pagination and sorting"
    )
    @GetMapping
    public Page<TrainerResponseDto> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        return trainerService.getAll(page, size, sortBy, direction);
    }

    @Operation(summary = "Update trainer")
    @PutMapping("/{id}")
    public TrainerResponseDto update(
            @PathVariable Long id,
            @Valid @RequestBody UpdateTrainerRequestDto requestDto
    ) {
        return trainerService.update(id, requestDto);
    }

    @Operation(summary = "Delete trainer")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        trainerService.delete(id);
    }
}