package com.fredodev.riee.treatment.infrastructure.ports.adapter;

import com.fredodev.riee.treatment.domain.entity.TreatmentHistoryStatusEntity;
import com.fredodev.riee.treatment.domain.repository.TreatmentHistoryStatusRepository;
import com.fredodev.riee.treatment.infrastructure.ports.persistence.JpaTreatmentHistoryStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TreatmentHistoryStatusAdapter implements TreatmentHistoryStatusRepository {

    private final JpaTreatmentHistoryStatusRepository jpaRepository;

    @Override
    public TreatmentHistoryStatusEntity save(TreatmentHistoryStatusEntity entity) {
        return jpaRepository.save(entity);
    }

    @Override
    public Optional<TreatmentHistoryStatusEntity> findById(Long id) {
        return jpaRepository.findById(id);
    }

    @Override
    public Optional<TreatmentHistoryStatusEntity> findByNombreEstado(String nombre) {
        return jpaRepository.findByNombreEstado(nombre);
    }

    @Override
    public List<TreatmentHistoryStatusEntity> findAll() {
        return jpaRepository.findAll();
    }
}
