package com.fredodev.riee.treatment.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TreatmentHistoryStatusRequest {

    @NotBlank(message = "El nombre del estado es obligatorio")
    @Size(max = 100)
    private String nombreEstado;

    @Size(max = 500)
    private String descripcion;

    private String codigoColor;
}
