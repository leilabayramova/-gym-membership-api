package com.example.gymmembershipapi.service;

import com.example.gymmembershipapi.dto.CreateTrainingProgramRequestDto;
import com.example.gymmembershipapi.dto.TrainingProgramResponseDto;
import com.example.gymmembershipapi.dto.UpdateTrainingProgramRequestDto;
import com.example.gymmembershipapi.entity.CategoryEntity;
import com.example.gymmembershipapi.entity.TrainerEntity;
import com.example.gymmembershipapi.entity.TrainingProgramEntity;
import com.example.gymmembershipapi.exception.ResourceNotFoundException;
import com.example.gymmembershipapi.mapper.TrainingProgramMapper;
import com.example.gymmembershipapi.repository.CategoryRepository;
import com.example.gymmembershipapi.repository.TrainerRepository;
import com.example.gymmembershipapi.repository.TrainingProgramRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class TrainingProgramService {

    private final TrainingProgramRepository trainingProgramRepository;
    private final TrainerRepository trainerRepository;
    private final CategoryRepository categoryRepository;

    public TrainingProgramResponseDto create(
            CreateTrainingProgramRequestDto requestDto
    ) {
        TrainerEntity trainer = trainerRepository
                .findById(requestDto.getTrainerId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Trainer not found with id: "
                                        + requestDto.getTrainerId()
                        )
                );

        Set<CategoryEntity> categories = new HashSet<>(
                categoryRepository.findAllById(requestDto.getCategoryIds())
        );

        if (categories.size() != requestDto.getCategoryIds().size()) {
            throw new ResourceNotFoundException(
                    "One or more categories were not found"
            );
        }

        TrainingProgramEntity trainingProgram =
                TrainingProgramMapper.toEntity(
                        requestDto,
                        trainer,
                        categories
                );

        TrainingProgramEntity savedTrainingProgram =
                trainingProgramRepository.save(trainingProgram);

        return TrainingProgramMapper.toResponseDto(savedTrainingProgram);
    }

    public TrainingProgramResponseDto getById(Long id) {
        TrainingProgramEntity trainingProgram =
                trainingProgramRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Training program not found with id: " + id
                                )
                        );

        return TrainingProgramMapper.toResponseDto(trainingProgram);
    }

    public Page<TrainingProgramResponseDto> getAll(
            int page,
            int size,
            String sortBy,
            String direction
    ) {
        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return trainingProgramRepository.findAll(pageable)
                .map(TrainingProgramMapper::toResponseDto);
    }

    public TrainingProgramResponseDto update(
            Long id,
            UpdateTrainingProgramRequestDto requestDto
    ) {
        TrainingProgramEntity trainingProgram =
                trainingProgramRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Training program not found with id: " + id
                                )
                        );

        TrainerEntity trainer = trainerRepository
                .findById(requestDto.getTrainerId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Trainer not found with id: "
                                        + requestDto.getTrainerId()
                        )
                );

        TrainingProgramMapper.updateEntity(
                trainingProgram,
                requestDto,
                trainer
        );

        TrainingProgramEntity updatedTrainingProgram =
                trainingProgramRepository.save(trainingProgram);

        return TrainingProgramMapper.toResponseDto(updatedTrainingProgram);
    }

    public void delete(Long id) {
        TrainingProgramEntity trainingProgram =
                trainingProgramRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Training program not found with id: " + id
                                )
                        );

        trainingProgramRepository.delete(trainingProgram);
    }
}