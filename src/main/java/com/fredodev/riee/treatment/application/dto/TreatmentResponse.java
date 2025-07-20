package com.fredodev.riee.treatment.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TreatmentResponse {
    private Long id;
    private String nombreTratamiento;
    private String descripcion;
    private String procedimiento;
    private int semanasEstimadas;
    private int costoBaseTratamiento;
    private String notasAdicionales;
}
