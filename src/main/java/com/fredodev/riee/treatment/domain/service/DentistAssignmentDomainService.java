package com.fredodev.riee.treatment.domain.service;

import com.fredodev.riee.treatment.domain.entity.DentistTreatmentHistoryEntity;
import com.fredodev.riee.treatment.domain.repository.DentistTreatmentHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DentistAssignmentDomainService {

    private final DentistTreatmentHistoryRepository dentistTreatmentHistoryRepository;

    public DentistTreatmentHistoryEntity assignDentist(DentistTreatmentHistoryEntity entity) {
        return dentistTreatmentHistoryRepository.save(entity);
    }

    public Optional<DentistTreatmentHistoryEntity> findPrincipalDentist(Long treatmentHistoryId) {
        return dentistTreatmentHistoryRepository.findByTreatmentHistoryIdAndDentistaPrincipalTrue(treatmentHistoryId);
    }

    public List<DentistTreatmentHistoryEntity> findAssignedDentists(Long treatmentHistoryId) {
        return dentistTreatmentHistoryRepository.findByTreatmentHistoryId(treatmentHistoryId);
    }

    public void removeAssignment(Long assignmentId) {
        dentistTreatmentHistoryRepository.deleteById(assignmentId);
    }
}
