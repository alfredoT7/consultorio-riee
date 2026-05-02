package com.fredodev.riee.treatment.infrastructure.ports.adapter;

import com.fredodev.riee.treatment.domain.entity.DentistTreatmentHistoryEntity;
import com.fredodev.riee.treatment.domain.repository.DentistTreatmentHistoryRepository;
import com.fredodev.riee.treatment.infrastructure.ports.persistence.JpaDentistTreatmentHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class DentistTreatmentHistoryAdapter implements DentistTreatmentHistoryRepository {

    private final JpaDentistTreatmentHistoryRepository jpaRepository;

    @Override
    public DentistTreatmentHistoryEntity save(DentistTreatmentHistoryEntity entity) {
        return jpaRepository.save(entity);
    }

    @Override
    public Optional<DentistTreatmentHistoryEntity> findById(Long id) {
        return jpaRepository.findById(id);
    }

    @Override
    public List<DentistTreatmentHistoryEntity> findByTreatmentHistoryId(Long treatmentHistoryId) {
        return jpaRepository.findByTreatmentHistoryId(treatmentHistoryId);
    }

    @Override
    public Optional<DentistTreatmentHistoryEntity> findByTreatmentHistoryIdAndDentistaPrincipalTrue(Long treatmentHistoryId) {
        return jpaRepository.findByTreatmentHistoryIdAndDentistaPrincipalTrue(treatmentHistoryId);
    }

    @Override
    public List<DentistTreatmentHistoryEntity> findByDentistIdAndDentistaPrincipalTrue(Long dentistId) {
        return jpaRepository.findByDentistIdAndDentistaPrincipalTrue(dentistId);
    }

    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }
}
