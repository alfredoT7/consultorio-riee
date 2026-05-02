package com.fredodev.riee.treatment.application.usecases;

import com.fredodev.riee.treatment.application.dto.DentistTreatmentHistoryRequest;
import com.fredodev.riee.treatment.application.dto.DentistTreatmentHistoryResponse;
import com.fredodev.riee.treatment.domain.entity.DentistTreatmentHistoryEntity;
import com.fredodev.riee.treatment.domain.entity.TreatmentHistoryEntity;
import com.fredodev.riee.treatment.domain.exception.DentistNotAssignedException;
import com.fredodev.riee.treatment.domain.service.DentistAssignmentDomainService;
import com.fredodev.riee.treatment.domain.service.TreatmentHistoryDomainService;
import com.fredodev.riee.dentist.domain.entity.DentistEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class DentistAssignmentUseCase {

    private final DentistAssignmentDomainService assignmentDomainService;
    private final TreatmentHistoryDomainService treatmentHistoryDomainService;

    @Transactional
    public DentistTreatmentHistoryResponse assignDentist(DentistTreatmentHistoryRequest request) {
        TreatmentHistoryEntity history = treatmentHistoryDomainService.getOrThrow(request.getTreatmentHistoryId());

        DentistEntity dentist = new DentistEntity();
        dentist.setId(request.getDentistId());

        DentistTreatmentHistoryEntity entity = DentistTreatmentHistoryEntity.builder()
                .treatmentHistory(history)
                .dentist(dentist)
                .dentistaPrincipal(request.getDentistaPrincipal() != null ? request.getDentistaPrincipal() : false)
                .notas(request.getNotas())
                .build();

        DentistTreatmentHistoryEntity saved = assignmentDomainService.assignDentist(entity);
        return mapToResponse(saved);
    }

    @Transactional(readOnly = true)
    public List<DentistTreatmentHistoryResponse> getAssignedDentists(Long treatmentHistoryId) {
        return assignmentDomainService.findAssignedDentists(treatmentHistoryId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public DentistTreatmentHistoryResponse getPrincipalDentist(Long treatmentHistoryId) {
        return assignmentDomainService.findPrincipalDentist(treatmentHistoryId)
                .map(this::mapToResponse)
                .orElseThrow(() -> new DentistNotAssignedException(treatmentHistoryId));
    }

    @Transactional
    public void removeAssignment(Long assignmentId) {
        assignmentDomainService.removeAssignment(assignmentId);
    }

    private DentistTreatmentHistoryResponse mapToResponse(DentistTreatmentHistoryEntity entity) {
        String dentistNombre = null;
        String dentistEmail = null;
        Long dentistId = null;

        if (entity.getDentist() != null) {
            dentistId = entity.getDentist().getId();
            dentistNombre = entity.getDentist().getNombres() + " " + entity.getDentist().getApellidos();
            dentistEmail = entity.getDentist().getEmail();
        }

        return DentistTreatmentHistoryResponse.builder()
                .id(entity.getId())
                .treatmentHistoryId(entity.getTreatmentHistory() != null ? entity.getTreatmentHistory().getId() : null)
                .dentistId(dentistId)
                .dentistNombre(dentistNombre)
                .dentistEmail(dentistEmail)
                .dentistaPrincipal(entity.getDentistaPrincipal())
                .notas(entity.getNotas())
                .fechaAsignacion(entity.getFechaAsignacion())
                .build();
    }
}
