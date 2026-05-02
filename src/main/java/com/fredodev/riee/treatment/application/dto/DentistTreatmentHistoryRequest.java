package com.fredodev.riee.treatment.application.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DentistTreatmentHistoryRequest {

    private Long treatmentHistoryId;

    @NotNull(message = "El ID del dentista es obligatorio")
    private Long dentistId;

    @Builder.Default
    private Boolean dentistaPrincipal = false;

    @Size(max = 500)
    private String notas;
}
