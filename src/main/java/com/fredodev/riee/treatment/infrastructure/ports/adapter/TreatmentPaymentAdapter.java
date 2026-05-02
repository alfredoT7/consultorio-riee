package com.fredodev.riee.treatment.infrastructure.ports.adapter;

import com.fredodev.riee.treatment.domain.entity.TreatmentPaymentEntity;
import com.fredodev.riee.treatment.domain.repository.TreatmentPaymentRepository;
import com.fredodev.riee.treatment.infrastructure.ports.persistence.JpaTreatmentPaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class TreatmentPaymentAdapter implements TreatmentPaymentRepository {

    private final JpaTreatmentPaymentRepository jpaRepository;

    @Override
    public TreatmentPaymentEntity save(TreatmentPaymentEntity payment) {
        return jpaRepository.save(payment);
    }

    @Override
    public List<TreatmentPaymentEntity> findByTreatmentHistoryId(Long treatmentHistoryId) {
        return jpaRepository.findByTreatmentHistoryIdOrderByFechaPagoDescIdDesc(treatmentHistoryId);
    }
}
