package com.example.gymmembershipapi.controller;

import com.example.gymmembershipapi.service.TrainerDocumentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(
        name = "Trainer Documents",
        description = "APIs for uploading and downloading trainer certificates and qualification documents"
)
public class TrainerDocumentController {

    private final TrainerDocumentService trainerDocumentService;

    @Operation(
            summary = "Upload trainer document",
            description = "Uploads a PDF, PNG or JPEG trainer certificate or qualification document. Maximum allowed file size is 5 MB."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Document uploaded successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Unsupported or invalid file"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Trainer not found"
            ),
            @ApiResponse(
                    responseCode = "413",
                    description = "File size exceeds 5 MB"
            )
    })
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
            description = "Downloads an uploaded trainer certificate or qualification document."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Document downloaded successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Trainer or document not found"
            )
    })
    @GetMapping("/{trainerId}/documents/{fileName}")
    public ResponseEntity<Resource> downloadDocument(
            @PathVariable Long trainerId,
            @PathVariable String fileName
    ) throws IOException {

        Resource resource =
                trainerDocumentService.download(trainerId, fileName);

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" +
                                resource.getFilename() + "\""
                )
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }
}