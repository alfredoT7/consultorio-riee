package com.fredodev.riee.cloudinary.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CloudinaryUploadResponse {
    private String url;
    private String publicId;
    private String originalFilename;
    private String format;
    private Long bytes;
    private Integer width;
    private Integer height;
}
