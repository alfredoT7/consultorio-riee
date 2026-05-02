package com.fredodev.riee.treatment.application.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TreatmentHistoryRequest {

    @NotNull(message = "El ID del paciente es obligatorio")
    private Long patientId;

    @NotNull(message = "El ID del tratamiento es obligatorio")
    private Long treatmentId;

    @NotNull(message = "La fecha de inicio es obligatoria")
    private Date fechaInicioTratamiento;

    private Date fechaFinTratamiento;

    @Size(max = 500)
    private String notasAdicionalesTratamientoPaciente;

    @NotNull(message = "El precio de venta es obligatorio")
    private Double precioCosto;

    private Double costoTotalTratamiento;

    @Min(value = 0, message = "El descuento no puede ser negativo")
    @Builder.Default
    private Double descuentoTotalTratamiento = 0.0;

    private List<Long> dentalPieceIds;
}
