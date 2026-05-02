package com.fredodev.riee.treatment.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TreatmentHistoryImageRequest {

    @NotNull(message = "El ID del historial es obligatorio")
    private Long treatmentHistoryId;

    @NotBlank(message = "La URL de la imagen es obligatoria")
    private String imageUrl;
}
