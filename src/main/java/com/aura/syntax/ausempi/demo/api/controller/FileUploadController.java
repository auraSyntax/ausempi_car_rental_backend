package com.aura.syntax.ausempi.demo.api.controller;

import com.aura.syntax.ausempi.demo.service.FileUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/file-upload")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:5173"})
public class FileUploadController {

    private final FileUploadService fileUploadService;

    @PostMapping("/upload-video")
    public ResponseEntity<?> uploadVideo(@RequestParam("file") MultipartFile file) {
        try {
            if (!file.getContentType().matches("video/(mp4|mov|avi|mkv)")) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid video type");
            }

            Map uploadResult = fileUploadService.uploadVideo(file);

            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "data", Map.of(
                            "url", uploadResult.get("secure_url"),
                            "public_id", uploadResult.get("public_id")
                    )
            ));
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to upload video");
        }
    }

    @DeleteMapping
    public ResponseEntity<Map<String, String>> deleteFile(@RequestParam(value = "url") String url) {
        String message = fileUploadService.deleteFile(url);
        return ResponseEntity.ok(Collections.singletonMap("message", message));
    }
}
