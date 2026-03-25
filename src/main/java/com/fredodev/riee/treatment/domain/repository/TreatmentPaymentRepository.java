package com.fredodev.riee.treatment.domain.repository;

import com.fredodev.riee.treatment.domain.entity.TreatmentPaymentEntity;

import java.util.List;

public interface TreatmentPaymentRepository {
    TreatmentPaymentEntity save(TreatmentPaymentEntity payment);
    List<TreatmentPaymentEntity> findByTreatmentHistoryId(Long treatmentHistoryId);
}
