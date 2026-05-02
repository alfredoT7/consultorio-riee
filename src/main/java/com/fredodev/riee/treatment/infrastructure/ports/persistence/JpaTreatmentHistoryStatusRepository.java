package com.fredodev.riee.treatment.infrastructure.ports.persistence;

import com.fredodev.riee.treatment.domain.entity.TreatmentHistoryStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaTreatmentHistoryStatusRepository extends JpaRepository<TreatmentHistoryStatusEntity, Long> {
    Optional<TreatmentHistoryStatusEntity> findByNombreEstado(String nombreEstado);
}
