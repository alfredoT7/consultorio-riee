package com.fredodev.riee.treatment.infrastructure.ports.persistence;

import com.fredodev.riee.treatment.domain.entity.TreatmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaTreatmentRepository extends JpaRepository<TreatmentEntity, Long> {
    boolean existsByNombreTratamiento(String nombreTratamiento);
}
