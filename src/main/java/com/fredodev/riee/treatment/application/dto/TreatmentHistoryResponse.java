package com.fredodev.riee.treatment.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TreatmentHistoryResponse {
    private Long id;
    private Long patientId;
    private String patientNombre;
    private Long treatmentId;
    private String nombreTratamiento;
    private Date fechaInicioTratamiento;
    private Date fechaFinTratamiento;
    private String notasAdicionalesTratamientoPaciente;
    private Double precioTotalTratamiento;
    private Double costoTotalTratamiento;
    private Double descuentoTotalTratamiento;
    private Double saldoTotalTratamiento;
    private Long estadoId;
    private String estadoNombre;
    private List<DentalPieceResponse> dentalPieces;
    private LocalDateTime createdAt;
}
