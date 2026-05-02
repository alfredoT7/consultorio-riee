package com.fredodev.riee.treatment.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TreatmentHistoryImageResponse {
    private Long id;
    private Long treatmentHistoryId;
    private String imageUrl;
    private LocalDateTime createdAt;
}
