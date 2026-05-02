package com.fredodev.riee.treatment.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TreatmentHistoryStatusResponse {
    private Long id;
    private String nombreEstado;
    private String descripcion;
    private String codigoColor;
}
