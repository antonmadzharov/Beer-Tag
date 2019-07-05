package com.telerikacademy.beertag.controllers;

import com.telerikacademy.beertag.models.Image;
import com.telerikacademy.beertag.payload.UploadFileResponse;
import com.telerikacademy.beertag.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/userImage")
public class ImageController {

    private final ImageService imageService;


    @PostMapping("/uploadImage")
    public UploadFileResponse uploadFile(@RequestParam("file") final MultipartFile file) {
        Image dbFile = imageService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path("dbFile.getUserImageId()")
                .toUriString();

        return new UploadFileResponse(dbFile.getFileName(), fileDownloadUri,
                file.getContentType(), file.getSize());
    }

    @PostMapping("/uploadMultipleImages")
    public List<UploadFileResponse> uploadMultipleFiles(@RequestParam("files") final MultipartFile[] files) {
        return Arrays.stream(files)
                .map(this::uploadFile)
                .collect(Collectors.toList());
    }

    @GetMapping("/downloadImage/{imageId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable final int imageId) {
        Image dbFile = imageService.getFile(imageId);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(dbFile.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + dbFile.getFileName() + "\"")
                .body(new ByteArrayResource(dbFile.getData()));
    }
}