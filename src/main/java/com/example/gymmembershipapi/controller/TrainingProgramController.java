package com.example.gymmembershipapi.controller;

import com.example.gymmembershipapi.dto.CreateTrainingProgramRequestDto;
import com.example.gymmembershipapi.dto.TrainingProgramResponseDto;
import com.example.gymmembershipapi.dto.UpdateTrainingProgramRequestDto;
import com.example.gymmembershipapi.exception.ErrorResponse;
import com.example.gymmembershipapi.service.TrainingProgramService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(
            summary = "Create a new training program",
            description = "Creates a new training program and assigns it to an existing trainer"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Training program created successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = TrainingProgramResponseDto.class
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Request validation failed",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = ErrorResponse.class
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Trainer not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = ErrorResponse.class
                            )
                    )
            )
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TrainingProgramResponseDto create(
            @Valid @RequestBody
            CreateTrainingProgramRequestDto requestDto
    ) {
        return trainingProgramService.create(requestDto);
    }

    @Operation(
            summary = "Get training program by id",
            description = "Returns a training program using the provided id"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Training program found successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = TrainingProgramResponseDto.class
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Training program not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = ErrorResponse.class
                            )
                    )
            )
    })
    @GetMapping("/{id}")
    public TrainingProgramResponseDto getById(
            @Parameter(
                    description = "Unique identifier of the training program",
                    example = "1"
            )
            @PathVariable Long id
    ) {
        return trainingProgramService.getById(id);
    }

    @Operation(
            summary = "Get all training programs",
            description = "Returns training programs with pagination and sorting"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Paginated training programs returned successfully"
    )
    @GetMapping
    public Page<TrainingProgramResponseDto> getAll(
            @Parameter(
                    description = "Page number, starting from zero",
                    example = "0"
            )
            @RequestParam(defaultValue = "0") int page,

            @Parameter(
                    description = "Number of records per page",
                    example = "10"
            )
            @RequestParam(defaultValue = "10") int size,

            @Parameter(
                    description = "Field used for sorting",
                    example = "id"
            )
            @RequestParam(defaultValue = "id") String sortBy,

            @Parameter(
                    description = "Sorting direction: asc or desc",
                    example = "asc"
            )
            @RequestParam(defaultValue = "asc") String direction
    ) {
        return trainingProgramService.getAll(
                page,
                size,
                sortBy,
                direction
        );
    }

    @Operation(
            summary = "Update training program",
            description = "Updates an existing training program using the provided id"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Training program updated successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = TrainingProgramResponseDto.class
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Request validation failed",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = ErrorResponse.class
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Training program or trainer not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = ErrorResponse.class
                            )
                    )
            )
    })
    @PutMapping("/{id}")
    public TrainingProgramResponseDto update(
            @Parameter(
                    description = "Unique identifier of the training program",
                    example = "1"
            )
            @PathVariable Long id,

            @Valid @RequestBody
            UpdateTrainingProgramRequestDto requestDto
    ) {
        return trainingProgramService.update(id, requestDto);
    }

    @Operation(
            summary = "Delete training program",
            description = "Deletes an existing training program using the provided id"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Training program deleted successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Training program not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = ErrorResponse.class
                            )
                    )
            )
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @Parameter(
                    description = "Unique identifier of the training program",
                    example = "1"
            )
            @PathVariable Long id
    ) {
        trainingProgramService.delete(id);
    }
}