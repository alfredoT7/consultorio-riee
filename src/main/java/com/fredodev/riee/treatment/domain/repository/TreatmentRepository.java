package com.fredodev.riee.treatment.domain.repository;

import com.fredodev.riee.treatment.domain.entity.TreatmentEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface TreatmentRepository {
    TreatmentEntity save(TreatmentEntity treatment);
    Optional<TreatmentEntity> findById(Long id);
    List<TreatmentEntity> findAll();
    void deleteById(Long id);
    boolean existsByNombreTratamiento(String nombreTratamiento);
}