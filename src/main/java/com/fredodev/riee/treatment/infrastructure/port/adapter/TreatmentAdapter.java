package com.fredodev.riee.treatment.infrastructure.port.adapter;

import com.fredodev.riee.treatment.infrastructure.port.persistence.JpaTreatmentRepository;
import org.springframework.stereotype.Repository;

import com.fredodev.riee.treatment.domain.entity.TreatmentEntity;
import com.fredodev.riee.treatment.domain.repository.TreatmentRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TreatmentAdapter implements TreatmentRepository {
    private final JpaTreatmentRepository jpaTreatmentRepository;

    @Override
    public TreatmentEntity save(TreatmentEntity treatment) {
        return jpaTreatmentRepository.save(treatment);
    }

    @Override
    public Optional<TreatmentEntity> findById(Long id) {
        return jpaTreatmentRepository.findById(id);
    }

    @Override
    public List<TreatmentEntity> findAll() {
        return jpaTreatmentRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        jpaTreatmentRepository.deleteById(id);
    }

    @Override
    public boolean existsByNombreTratamiento(String nombreTratamiento) {
        return jpaTreatmentRepository.existsByNombreTratamiento(nombreTratamiento);
    }
}
