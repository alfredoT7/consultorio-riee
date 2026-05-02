package com.fredodev.riee.treatment.infrastructure.ports.adapter;

import com.fredodev.riee.treatment.domain.entity.TreatmentHistoryImageEntity;
import com.fredodev.riee.treatment.domain.repository.TreatmentHistoryImageRepository;
import com.fredodev.riee.treatment.infrastructure.ports.persistence.JpaTreatmentHistoryImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TreatmentHistoryImageAdapter implements TreatmentHistoryImageRepository {

    private final JpaTreatmentHistoryImageRepository jpaRepository;

    @Override
    public TreatmentHistoryImageEntity save(TreatmentHistoryImageEntity entity) {
        return jpaRepository.save(entity);
    }

    @Override
    public Optional<TreatmentHistoryImageEntity> findById(Long id) {
        return jpaRepository.findById(id);
    }

    @Override
    public List<TreatmentHistoryImageEntity> findByTreatmentHistoryId(Long treatmentHistoryId) {
        return jpaRepository.findByTreatmentHistoryId(treatmentHistoryId);
    }

    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }
}
