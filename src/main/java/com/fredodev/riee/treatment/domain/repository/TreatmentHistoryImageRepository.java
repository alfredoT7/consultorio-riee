package com.fredodev.riee.treatment.domain.repository;

import com.fredodev.riee.treatment.domain.entity.TreatmentHistoryImageEntity;

import java.util.List;
import java.util.Optional;

public interface TreatmentHistoryImageRepository {
    TreatmentHistoryImageEntity save(TreatmentHistoryImageEntity entity);
    Optional<TreatmentHistoryImageEntity> findById(Long id);
    List<TreatmentHistoryImageEntity> findByTreatmentHistoryId(Long treatmentHistoryId);
    void deleteById(Long id);
}
