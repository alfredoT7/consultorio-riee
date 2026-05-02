package com.fredodev.riee.treatment.infrastructure.ports.adapter;

import com.fredodev.riee.treatment.domain.entity.TreatmentHistoryEntity;
import com.fredodev.riee.treatment.domain.repository.TreatmentHistoryRepository;
import com.fredodev.riee.treatment.infrastructure.ports.persistence.JpaTreatmentHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TreatmentHistoryAdapter implements TreatmentHistoryRepository {

    private final JpaTreatmentHistoryRepository jpaRepository;

    @Override
    public TreatmentHistoryEntity save(TreatmentHistoryEntity entity) {
        return jpaRepository.save(entity);
    }

    @Override
    public Optional<TreatmentHistoryEntity> findById(Long id) {
        return jpaRepository.findById(id);
    }

    @Override
    public List<TreatmentHistoryEntity> findByPatientId(Long patientId) {
        return jpaRepository.findByPatientId(patientId);
    }

    @Override
    public List<TreatmentHistoryEntity> findByPatientIdAndEstadoTratamientoId(Long patientId, Long statusId) {
        return jpaRepository.findByPatientIdAndEstadoTratamientoId(patientId, statusId);
    }

    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }
}
