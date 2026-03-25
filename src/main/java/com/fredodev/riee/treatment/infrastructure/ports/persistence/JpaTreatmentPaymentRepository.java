package com.fredodev.riee.treatment.infrastructure.ports.persistence;

import com.fredodev.riee.treatment.domain.entity.TreatmentPaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaTreatmentPaymentRepository extends JpaRepository<TreatmentPaymentEntity, Long> {
    List<TreatmentPaymentEntity> findByTreatmentHistoryIdOrderByFechaPagoDescIdDesc(Long treatmentHistoryId);
}
