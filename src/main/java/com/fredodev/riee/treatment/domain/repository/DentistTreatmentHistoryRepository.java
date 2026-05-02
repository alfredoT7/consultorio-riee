package com.fredodev.riee.treatment.domain.repository;

import com.fredodev.riee.treatment.domain.entity.DentistTreatmentHistoryEntity;

import java.util.List;
import java.util.Optional;

public interface DentistTreatmentHistoryRepository {
    DentistTreatmentHistoryEntity save(DentistTreatmentHistoryEntity entity);
    Optional<DentistTreatmentHistoryEntity> findById(Long id);
    List<DentistTreatmentHistoryEntity> findByTreatmentHistoryId(Long treatmentHistoryId);
    Optional<DentistTreatmentHistoryEntity> findByTreatmentHistoryIdAndDentistaPrincipalTrue(Long treatmentHistoryId);
    List<DentistTreatmentHistoryEntity> findByDentistIdAndDentistaPrincipalTrue(Long dentistId);
    void deleteById(Long id);
}
