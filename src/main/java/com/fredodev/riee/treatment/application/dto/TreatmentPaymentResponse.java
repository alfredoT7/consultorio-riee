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
public class TreatmentPaymentResponse {
    private Long id;
    private Long treatmentHistoryId;
    private BigDecimal montoPago;
    private LocalDateTime fechaPago;
    private String metodoPago;
    private String referenciaPago;
    private String estadoPago;
    private BigDecimal saldoAnterior;
    private BigDecimal saldoPosterior;
    private String registradoPor;
    private String observacion;
    private LocalDateTime createdAt;
}
