package com.fredodev.riee.treatment.infrastructure.ports.persistence;

import com.fredodev.riee.treatment.domain.entity.TreatmentHistoryImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaTreatmentHistoryImageRepository extends JpaRepository<TreatmentHistoryImageEntity, Long> {
    List<TreatmentHistoryImageEntity> findByTreatmentHistoryId(Long treatmentHistoryId);
}
