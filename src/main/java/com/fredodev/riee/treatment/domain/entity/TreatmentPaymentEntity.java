package com.fredodev.riee.treatment.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "treatment_payments")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TreatmentPaymentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "treatment_history_id", nullable = false)
    private TreatmentHistoryEntity treatmentHistory;

    @Column(name = "monto_pago", nullable = false, precision = 12, scale = 2)
    private BigDecimal montoPago;

    @Column(name = "fecha_pago", nullable = false)
    private LocalDateTime fechaPago;

    @Column(name = "metodo_pago", nullable = false, length = 50)
    private String metodoPago;

    @Column(name = "referencia_pago", length = 100)
    private String referenciaPago;

    @Column(name = "estado_pago", nullable = false, length = 30)
    private String estadoPago;

    @Column(name = "saldo_anterior", precision = 12, scale = 2)
    private BigDecimal saldoAnterior;

    @Column(name = "saldo_posterior", precision = 12, scale = 2)
    private BigDecimal saldoPosterior;

    @Column(name = "registrado_por", length = 100)
    private String registradoPor;

    @Column(name = "observacion", length = 500)
    private String observacion;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        createdAt = now;
        updatedAt = now;
        if (fechaPago == null) {
            fechaPago = now;
        }
        if (estadoPago == null || estadoPago.isBlank()) {
            estadoPago = "REGISTRADO";
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
