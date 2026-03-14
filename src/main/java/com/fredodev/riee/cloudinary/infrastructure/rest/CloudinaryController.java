package com.fredodev.riee.cloudinary.infrastructure.rest;

import com.fredodev.riee.cloudinary.application.dto.CloudinaryUploadResponse;
import com.fredodev.riee.cloudinary.application.service.CloudinaryService;
import com.fredodev.riee.shared.api.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/cloudinary")
@RequiredArgsConstructor
public class CloudinaryController {

    private final CloudinaryService cloudinaryService;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<CloudinaryUploadResponse>> uploadImage(
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "folder", required = false) String folder
    ) {
        CloudinaryUploadResponse response = cloudinaryService.uploadImage(file, folder);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok(HttpStatus.CREATED.value(), "Imagen subida correctamente", response));
    }

    @PostMapping(value = "/replace", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<CloudinaryUploadResponse>> replaceImage(
            @RequestParam("file") MultipartFile file,
            @RequestParam("oldPublicId") String oldPublicId,
            @RequestParam(value = "folder", required = false) String folder
    ) {
        CloudinaryUploadResponse response = cloudinaryService.replaceImage(file, folder, oldPublicId);
        return ResponseEntity.ok(ApiResponse.ok(HttpStatus.OK.value(), "Imagen reemplazada correctamente", response));
    }

    @DeleteMapping
    public ResponseEntity<ApiResponse<Void>> deleteImage(@RequestParam("publicId") String publicId) {
        cloudinaryService.deleteImage(publicId);
        return ResponseEntity.ok(ApiResponse.ok(HttpStatus.OK.value(), "Imagen eliminada correctamente", null));
    }
}
