package com.fredodev.riee.treatment.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TreatmentRequest {
    @NotBlank(message = "El nombre del tratamiento es obligatorio")
    @Size(max = 255, message = "El nombre del tratamiento debe tener máximo 255 caracteres")
    private String nombreTratamiento;

    @Size(max = 500, message = "La descripción debe tener máximo 500 caracteres")
    private String descripcion;

    @Size(max = 1000, message = "El procedimiento debe tener máximo 1000 caracteres")
    private String procedimiento;

    @NotNull(message = "Las semanas estimadas son obligatorias")
    @Min(value = 1, message = "Las semanas estimadas deben ser al menos 1")
    private Integer semanasEstimadas;

    @NotNull(message = "El costo base es obligatorio")
    @Min(value = 0, message = "El costo base no puede ser negativo")
    private Integer costoBaseTratamiento;

    @Size(max = 500, message = "Las notas adicionales deben tener máximo 500 caracteres")
    private String notasAdicionales;
}