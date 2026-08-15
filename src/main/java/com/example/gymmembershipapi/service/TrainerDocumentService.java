package com.example.gymmembershipapi.service;

import com.example.gymmembershipapi.exception.FileTooLargeException;
import com.example.gymmembershipapi.exception.ResourceNotFoundException;
import com.example.gymmembershipapi.repository.TrainerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
@RequiredArgsConstructor
public class TrainerDocumentService {

    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024;

    private final TrainerRepository trainerRepository;

    private final Path uploadDirectory =
            Paths.get("uploads", "trainers");

    public String upload(
            Long trainerId,
            MultipartFile file
    ) throws IOException {

        validateTrainer(trainerId);
        validateFile(file);

        Path trainerDirectory =
                uploadDirectory.resolve(trainerId.toString());

        Files.createDirectories(trainerDirectory);

        String fileName = Path.of(file.getOriginalFilename())
                .getFileName()
                .toString();

        Path targetPath =
                trainerDirectory.resolve(fileName);

        Files.copy(
                file.getInputStream(),
                targetPath,
                StandardCopyOption.REPLACE_EXISTING
        );

        return fileName;
    }

    public Resource download(
            Long trainerId,
            String fileName
    ) throws IOException {

        validateTrainer(trainerId);

        Path trainerDirectory =
                uploadDirectory.resolve(trainerId.toString());

        Path filePath = trainerDirectory
                .resolve(fileName)
                .normalize();

        Resource resource =
                new UrlResource(filePath.toUri());

        if (!resource.exists()) {
            throw new ResourceNotFoundException(
                    "Document not found: " + fileName
            );
        }

        return resource;
    }

    private void validateTrainer(Long trainerId) {
        if (!trainerRepository.existsById(trainerId)) {
            throw new ResourceNotFoundException(
                    "Trainer not found with id: " + trainerId
            );
        }
    }

    private void validateFile(MultipartFile file) {

        if (file.isEmpty()) {
            throw new IllegalArgumentException(
                    "File cannot be empty"
            );
        }

        if (file.getSize() > MAX_FILE_SIZE) {
            throw new FileTooLargeException(
                    "File size cannot exceed 5 MB"
            );
        }

        String contentType = file.getContentType();

        if (!isAllowedContentType(contentType)) {
            throw new IllegalArgumentException(
                    "Only PDF, PNG and JPEG files are allowed"
            );
        }
    }

    private boolean isAllowedContentType(String contentType) {
        return "application/pdf".equals(contentType)
                || "image/png".equals(contentType)
                || "image/jpeg".equals(contentType);
    }
}