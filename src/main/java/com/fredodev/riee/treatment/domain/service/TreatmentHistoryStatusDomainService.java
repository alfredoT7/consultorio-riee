package com.fredodev.riee.treatment.domain.service;

import com.fredodev.riee.treatment.domain.entity.TreatmentHistoryStatusEntity;
import com.fredodev.riee.treatment.domain.exception.InvalidTreatmentStatusException;
import com.fredodev.riee.treatment.domain.repository.TreatmentHistoryStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TreatmentHistoryStatusDomainService {

    private final TreatmentHistoryStatusRepository statusRepository;

    public TreatmentHistoryStatusEntity saveStatus(TreatmentHistoryStatusEntity entity) {
        return statusRepository.save(entity);
    }

    public TreatmentHistoryStatusEntity getByIdOrThrow(Long id) {
        return statusRepository.findById(id)
                .orElseThrow(() -> new InvalidTreatmentStatusException(id));
    }

    public Optional<TreatmentHistoryStatusEntity> findByNombreEstado(String nombre) {
        return statusRepository.findByNombreEstado(nombre);
    }

    public List<TreatmentHistoryStatusEntity> findAll() {
        return statusRepository.findAll();
    }
}
