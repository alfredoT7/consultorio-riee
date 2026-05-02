package com.fredodev.riee.treatment.infrastructure.ports.persistence;

import com.fredodev.riee.treatment.domain.entity.TreatmentHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaTreatmentHistoryRepository extends JpaRepository<TreatmentHistoryEntity, Long> {
    List<TreatmentHistoryEntity> findByPatientId(Long patientId);
    List<TreatmentHistoryEntity> findByPatientIdAndEstadoTratamientoId(Long patientId, Long statusId);
}
