package com.example.gymmembershipapi.service;

import com.example.gymmembershipapi.dto.CreateTrainingProgramRequestDto;
import com.example.gymmembershipapi.dto.TrainingProgramResponseDto;
import com.example.gymmembershipapi.dto.UpdateTrainingProgramRequestDto;
import com.example.gymmembershipapi.entity.TrainerEntity;
import com.example.gymmembershipapi.entity.TrainingProgramEntity;
import com.example.gymmembershipapi.exception.ResourceNotFoundException;
import com.example.gymmembershipapi.mapper.TrainingProgramMapper;
import com.example.gymmembershipapi.repository.TrainerRepository;
import com.example.gymmembershipapi.repository.TrainingProgramRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TrainingProgramService {

    private final TrainingProgramRepository trainingProgramRepository;
    private final TrainerRepository trainerRepository;

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

        TrainingProgramEntity trainingProgram =
                TrainingProgramMapper.toEntity(requestDto, trainer);

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

    public List<TrainingProgramResponseDto> getAll() {
        return trainingProgramRepository.findAll()
                .stream()
                .map(TrainingProgramMapper::toResponseDto)
                .toList();
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