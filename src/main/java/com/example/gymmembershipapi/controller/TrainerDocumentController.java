package com.example.gymmembershipapi.controller;

import com.example.gymmembershipapi.service.TrainerDocumentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/trainers")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class TrainerDocumentController {

    private final TrainerDocumentService trainerDocumentService;

    @Operation(
            summary = "Upload trainer document",
            description = "Uploads a trainer certificate or qualification document"
    )
    @PostMapping(
            value = "/{trainerId}/documents",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public String uploadDocument(
            @PathVariable Long trainerId,
            @RequestParam("file") MultipartFile file
    ) throws IOException {

        return trainerDocumentService.upload(trainerId, file);
    }

    @Operation(
            summary = "Download trainer document",
            description = "Downloads a trainer certificate or qualification document"
    )
    @GetMapping("/{trainerId}/documents/{fileName}")
    public ResponseEntity<Resource> downloadDocument(
            @PathVariable Long trainerId,
            @PathVariable String fileName
    ) throws IOException {

        Resource resource = trainerDocumentService.download(trainerId, fileName);

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + resource.getFilename() + "\""
                )
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }
}