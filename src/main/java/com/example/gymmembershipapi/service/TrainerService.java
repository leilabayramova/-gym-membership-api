package com.example.gymmembershipapi.service;

import com.example.gymmembershipapi.dto.CreateTrainerRequestDto;
import com.example.gymmembershipapi.dto.TrainerResponseDto;
import com.example.gymmembershipapi.dto.UpdateTrainerRequestDto;
import com.example.gymmembershipapi.entity.TrainerEntity;
import com.example.gymmembershipapi.exception.DuplicateResourceException;
import com.example.gymmembershipapi.exception.ResourceNotFoundException;
import com.example.gymmembershipapi.mapper.TrainerMapper;
import com.example.gymmembershipapi.repository.TrainerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TrainerService {

    private final TrainerRepository trainerRepository;

    public TrainerResponseDto create(CreateTrainerRequestDto requestDto) {

        if (trainerRepository.existsByEmailIgnoreCase(requestDto.getEmail())) {
            throw new DuplicateResourceException(
                    "Trainer with this email already exists"
            );
        }

        TrainerEntity trainer = TrainerMapper.toEntity(requestDto);
        TrainerEntity savedTrainer = trainerRepository.save(trainer);

        return TrainerMapper.toResponseDto(savedTrainer);
    }

    public TrainerResponseDto getById(Long id) {
        TrainerEntity trainer = trainerRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Trainer not found with id: " + id
                        )
                );

        return TrainerMapper.toResponseDto(trainer);
    }

    public List<TrainerResponseDto> getAll() {
        return trainerRepository.findAll()
                .stream()
                .map(TrainerMapper::toResponseDto)
                .toList();
    }

    public TrainerResponseDto update(
            Long id,
            UpdateTrainerRequestDto requestDto
    ) {
        TrainerEntity trainer = trainerRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Trainer not found with id: " + id
                        )
                );

        if (trainerRepository.existsByEmailIgnoreCaseAndIdNot(
                requestDto.getEmail(),
                id
        )) {
            throw new DuplicateResourceException(
                    "Trainer with this email already exists"
            );
        }

        TrainerMapper.updateEntity(trainer, requestDto);

        TrainerEntity updatedTrainer = trainerRepository.save(trainer);

        return TrainerMapper.toResponseDto(updatedTrainer);
    }

    public void delete(Long id) {
        TrainerEntity trainer = trainerRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Trainer not found with id: " + id
                        )
                );

        trainerRepository.delete(trainer);
    }
}