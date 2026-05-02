package com.fredodev.riee.treatment.domain.service;

import com.fredodev.riee.treatment.domain.entity.TreatmentHistoryEntity;
import com.fredodev.riee.treatment.domain.exception.TreatmentHistoryNotFoundException;
import com.fredodev.riee.treatment.domain.repository.TreatmentHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TreatmentHistoryDomainService {

    private final TreatmentHistoryRepository treatmentHistoryRepository;

    public TreatmentHistoryEntity saveTreatmentHistory(TreatmentHistoryEntity entity) {
        return treatmentHistoryRepository.save(entity);
    }

    public Optional<TreatmentHistoryEntity> getTreatmentHistoryById(Long id) {
        return treatmentHistoryRepository.findById(id);
    }

    public TreatmentHistoryEntity getOrThrow(Long id) {
        return treatmentHistoryRepository.findById(id)
                .orElseThrow(() -> new TreatmentHistoryNotFoundException(id));
    }

    public List<TreatmentHistoryEntity> getTreatmentHistoriesByPatientId(Long patientId) {
        return treatmentHistoryRepository.findByPatientId(patientId);
    }

    public void deleteTreatmentHistory(Long id) {
        getOrThrow(id);
        treatmentHistoryRepository.deleteById(id);
    }

    public void validateTreatmentHistoryData(TreatmentHistoryEntity entity) {
        if (entity.getPatient() == null) {
            throw new IllegalArgumentException("El paciente no puede ser nulo");
        }
        if (entity.getTreatment() == null) {
            throw new IllegalArgumentException("El tratamiento no puede ser nulo");
        }
        if (entity.getFechaInicioTratamiento() == null) {
            throw new IllegalArgumentException("La fecha de inicio no puede ser nula");
        }
    }
}
