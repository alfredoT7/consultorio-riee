package com.fredodev.riee.treatment.infrastructure.ports.persistence;

import com.fredodev.riee.treatment.domain.entity.DentistTreatmentHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JpaDentistTreatmentHistoryRepository extends JpaRepository<DentistTreatmentHistoryEntity, Long> {
    List<DentistTreatmentHistoryEntity> findByTreatmentHistoryId(Long treatmentHistoryId);
    Optional<DentistTreatmentHistoryEntity> findByTreatmentHistoryIdAndDentistaPrincipalTrue(Long treatmentHistoryId);
    List<DentistTreatmentHistoryEntity> findByDentistIdAndDentistaPrincipalTrue(Long dentistId);
}
