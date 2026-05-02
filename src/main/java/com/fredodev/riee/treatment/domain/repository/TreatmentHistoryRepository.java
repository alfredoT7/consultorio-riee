package com.fredodev.riee.treatment.domain.repository;

import com.fredodev.riee.treatment.domain.entity.TreatmentHistoryEntity;

import java.util.List;
import java.util.Optional;

public interface TreatmentHistoryRepository {
    TreatmentHistoryEntity save(TreatmentHistoryEntity entity);
    Optional<TreatmentHistoryEntity> findById(Long id);
    List<TreatmentHistoryEntity> findByPatientId(Long patientId);
    List<TreatmentHistoryEntity> findByPatientIdAndEstadoTratamientoId(Long patientId, Long statusId);
    void deleteById(Long id);
}
