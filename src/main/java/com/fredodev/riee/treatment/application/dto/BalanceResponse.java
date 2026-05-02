package com.fredodev.riee.treatment.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BalanceResponse {
    private Long treatmentHistoryId;
    private Double precioTotalTratamiento;
    private BigDecimal montoTotalPagado;
    private BigDecimal saldoPendiente;
    private LocalDateTime ultimoPago;
}
