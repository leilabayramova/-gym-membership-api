package com.example.gymmembershipapi.controller;

import com.example.gymmembershipapi.dto.CreateTrainerRequestDto;
import com.example.gymmembershipapi.dto.TrainerResponseDto;
import com.example.gymmembershipapi.dto.UpdateTrainerRequestDto;
import com.example.gymmembershipapi.exception.ErrorResponse;
import com.example.gymmembershipapi.service.TrainerService;
import io.swagger.v3.oas.annotations.Operation;
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
@RequestMapping("/api/trainers")
@RequiredArgsConstructor
@Tag(
        name = "Trainer",
        description = "Trainer management operations"
)
public class TrainerController {

    private final TrainerService trainerService;

    @Operation(
            summary = "Create a new trainer",
            description = "Creates a new trainer using the provided information"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Trainer created successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = TrainerResponseDto.class
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
                    responseCode = "409",
                    description = "Trainer with the same email already exists",
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
    public TrainerResponseDto create(
            @Valid @RequestBody CreateTrainerRequestDto requestDto
    ) {
        return trainerService.create(requestDto);
    }

    @Operation(
            summary = "Get trainer by id",
            description = "Returns a trainer using the provided trainer id"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Trainer found successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = TrainerResponseDto.class
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
    @GetMapping("/{id}")
    public TrainerResponseDto getById(@PathVariable Long id) {
        return trainerService.getById(id);
    }

    @Operation(
            summary = "Get all trainers",
            description = "Returns trainers with pagination and sorting"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Paginated trainers returned successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = Page.class
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Pagination or sorting parameters are invalid",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = ErrorResponse.class
                            )
                    )
            )
    })
    @GetMapping
    public Page<TrainerResponseDto> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        return trainerService.getAll(page, size, sortBy, direction);
    }

    @Operation(
            summary = "Update trainer",
            description = "Updates an existing trainer using the provided trainer id"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Trainer updated successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = TrainerResponseDto.class
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
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Another trainer with the same email already exists",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = ErrorResponse.class
                            )
                    )
            )
    })
    @PutMapping("/{id}")
    public TrainerResponseDto update(
            @PathVariable Long id,
            @Valid @RequestBody UpdateTrainerRequestDto requestDto
    ) {
        return trainerService.update(id, requestDto);
    }

    @Operation(
            summary = "Delete trainer",
            description = "Deletes a trainer if no related training programs exist"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Trainer deleted successfully"
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
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Trainer cannot be deleted because related training programs exist",
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
    public void delete(@PathVariable Long id) {
        trainerService.delete(id);
    }
}