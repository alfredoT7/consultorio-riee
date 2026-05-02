package com.fredodev.riee.treatment.domain.repository;

import com.fredodev.riee.treatment.domain.entity.TreatmentHistoryStatusEntity;

import java.util.List;
import java.util.Optional;

public interface TreatmentHistoryStatusRepository {
    TreatmentHistoryStatusEntity save(TreatmentHistoryStatusEntity entity);
    Optional<TreatmentHistoryStatusEntity> findById(Long id);
    Optional<TreatmentHistoryStatusEntity> findByNombreEstado(String nombre);
    List<TreatmentHistoryStatusEntity> findAll();
}
