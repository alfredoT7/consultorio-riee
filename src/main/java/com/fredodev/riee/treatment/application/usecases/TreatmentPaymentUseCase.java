package com.fredodev.riee.treatment.application.usecases;

import com.fredodev.riee.treatment.application.dto.BalanceResponse;
import com.fredodev.riee.treatment.application.dto.TreatmentPaymentRequest;
import com.fredodev.riee.treatment.application.dto.TreatmentPaymentResponse;
import com.fredodev.riee.treatment.domain.entity.TreatmentHistoryEntity;
import com.fredodev.riee.treatment.domain.entity.TreatmentPaymentEntity;
import com.fredodev.riee.treatment.domain.service.TreatmentHistoryDomainService;
import com.fredodev.riee.treatment.domain.service.TreatmentPaymentDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TreatmentPaymentUseCase {

    private final TreatmentPaymentDomainService paymentDomainService;
    private final TreatmentHistoryDomainService treatmentHistoryDomainService;

    @Transactional
    public TreatmentPaymentResponse registerPayment(TreatmentPaymentRequest request) {
        paymentDomainService.validatePaymentAmount(request.getMontoPago());

        TreatmentHistoryEntity history = treatmentHistoryDomainService.getOrThrow(request.getTreatmentHistoryId());

        BigDecimal saldoAnterior = history.getSaldoTotalTratamiento() != null
                ? BigDecimal.valueOf(history.getSaldoTotalTratamiento())
                : BigDecimal.ZERO;

        BigDecimal saldoPosterior = saldoAnterior.subtract(request.getMontoPago());

        TreatmentPaymentEntity payment = TreatmentPaymentEntity.builder()
                .treatmentHistory(history)
                .montoPago(request.getMontoPago())
                .metodoPago(request.getMetodoPago())
                .referenciaPago(request.getReferenciaPago())
                .observacion(request.getObservacion())
                .estadoPago("REGISTRADO")
                .saldoAnterior(saldoAnterior)
                .saldoPosterior(saldoPosterior)
                .build();

        TreatmentPaymentEntity saved = paymentDomainService.savePayment(payment);

        history.setSaldoTotalTratamiento(saldoPosterior.doubleValue());
        treatmentHistoryDomainService.saveTreatmentHistory(history);

        return mapToResponse(saved);
    }

    @Transactional(readOnly = true)
    public List<TreatmentPaymentResponse> getPaymentsByTreatmentHistory(Long treatmentHistoryId) {
        return paymentDomainService.getPaymentsByTreatmentHistoryId(treatmentHistoryId)
                .stream()
                .sorted(Comparator.comparing(TreatmentPaymentEntity::getFechaPago,
                        Comparator.nullsLast(Comparator.reverseOrder())))
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public BalanceResponse getBalance(Long treatmentHistoryId) {
        TreatmentHistoryEntity history = treatmentHistoryDomainService.getOrThrow(treatmentHistoryId);
        BigDecimal totalPagado = paymentDomainService.calculatePaymentSum(treatmentHistoryId);

        List<TreatmentPaymentEntity> payments = paymentDomainService.getPaymentsByTreatmentHistoryId(treatmentHistoryId);
        java.time.LocalDateTime ultimoPago = payments.stream()
                .map(TreatmentPaymentEntity::getFechaPago)
                .filter(f -> f != null)
                .max(Comparator.naturalOrder())
                .orElse(null);

        Double precio = history.getPrecioTotalTratamiento() != null ? history.getPrecioTotalTratamiento() : 0.0;
        BigDecimal saldoPendiente = BigDecimal.valueOf(precio).subtract(totalPagado);

        return BalanceResponse.builder()
                .treatmentHistoryId(treatmentHistoryId)
                .precioTotalTratamiento(precio)
                .montoTotalPagado(totalPagado)
                .saldoPendiente(saldoPendiente)
                .ultimoPago(ultimoPago)
                .build();
    }

    @Transactional(readOnly = true)
    public BigDecimal getTotalPaid(Long treatmentHistoryId) {
        return paymentDomainService.calculatePaymentSum(treatmentHistoryId);
    }

    private TreatmentPaymentResponse mapToResponse(TreatmentPaymentEntity entity) {
        return TreatmentPaymentResponse.builder()
                .id(entity.getId())
                .treatmentHistoryId(entity.getTreatmentHistory() != null ? entity.getTreatmentHistory().getId() : null)
                .montoPago(entity.getMontoPago())
                .fechaPago(entity.getFechaPago())
                .metodoPago(entity.getMetodoPago())
                .referenciaPago(entity.getReferenciaPago())
                .estadoPago(entity.getEstadoPago())
                .saldoAnterior(entity.getSaldoAnterior())
                .saldoPosterior(entity.getSaldoPosterior())
                .registradoPor(entity.getRegistradoPor())
                .observacion(entity.getObservacion())
                .createdAt(entity.getCreatedAt())
                .build();
    }
}
