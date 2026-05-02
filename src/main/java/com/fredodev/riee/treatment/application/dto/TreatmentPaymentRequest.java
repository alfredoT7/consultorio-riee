package com.fredodev.riee.treatment.application.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TreatmentPaymentRequest {

    private Long treatmentHistoryId;

    @NotNull(message = "El monto de pago es obligatorio")
    @DecimalMin(value = "0.01", message = "El monto debe ser mayor a 0")
    private BigDecimal montoPago;

    @NotNull(message = "El método de pago es obligatorio")
    private String metodoPago;

    @Size(max = 100)
    private String referenciaPago;

    @Size(max = 500)
    private String observacion;
}
