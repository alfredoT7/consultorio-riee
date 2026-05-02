package com.fredodev.riee.treatment.domain.service;

import com.fredodev.riee.treatment.domain.entity.TreatmentPaymentEntity;
import com.fredodev.riee.treatment.domain.exception.InvalidPaymentAmountException;
import com.fredodev.riee.treatment.domain.repository.TreatmentPaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TreatmentPaymentDomainService {

    private final TreatmentPaymentRepository treatmentPaymentRepository;

    public TreatmentPaymentEntity savePayment(TreatmentPaymentEntity entity) {
        return treatmentPaymentRepository.save(entity);
    }

    public List<TreatmentPaymentEntity> getPaymentsByTreatmentHistoryId(Long treatmentHistoryId) {
        return treatmentPaymentRepository.findByTreatmentHistoryId(treatmentHistoryId);
    }

    public BigDecimal calculatePaymentSum(Long treatmentHistoryId) {
        return treatmentPaymentRepository.findByTreatmentHistoryId(treatmentHistoryId)
                .stream()
                .map(TreatmentPaymentEntity::getMontoPago)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal calculateTotalBalance(Long treatmentHistoryId, Double precioTotal) {
        BigDecimal totalPagado = calculatePaymentSum(treatmentHistoryId);
        BigDecimal precio = precioTotal != null ? BigDecimal.valueOf(precioTotal) : BigDecimal.ZERO;
        return precio.subtract(totalPagado);
    }

    public void validatePaymentAmount(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidPaymentAmountException("El monto del pago debe ser mayor a 0");
        }
    }
}
