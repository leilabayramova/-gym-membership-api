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
import com.example.gymmembershipapi.specification.TrainingProgramSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
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
    @Cacheable(value = "trainingPrograms", key = "#id")
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

    public List<TrainingProgramResponseDto> filter(
            Long trainerId,
            Long categoryId,
            BigDecimal minPrice,
            BigDecimal maxPrice,
            Integer maxDurationInWeeks
    ) {
        return trainingProgramRepository.findByComplexFilter(
                        trainerId,
                        categoryId,
                        minPrice,
                        maxPrice,
                        maxDurationInWeeks
                )
                .stream()
                .map(TrainingProgramMapper::toResponseDto)
                .toList();
    }
    public List<TrainingProgramResponseDto> search(
            String name,
            Long trainerId,
            Long categoryId,
            BigDecimal minPrice,
            BigDecimal maxPrice,
            Integer maxDurationInWeeks
    ) {
        Specification<TrainingProgramEntity> specification =
                TrainingProgramSpecification.withFilters(
                        name,
                        trainerId,
                        categoryId,
                        minPrice,
                        maxPrice,
                        maxDurationInWeeks
                );

        return trainingProgramRepository.findAll(specification)
                .stream()
                .map(TrainingProgramMapper::toResponseDto)
                .toList();
    }
    public List<TrainingProgramResponseDto> getAllWithDetails() {
        return trainingProgramRepository.findAllWithDetails()
                .stream()
                .map(TrainingProgramMapper::toResponseDto)
                .toList();
    }
}