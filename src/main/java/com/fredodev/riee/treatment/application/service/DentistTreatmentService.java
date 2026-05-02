package com.fredodev.riee.treatment.application.service;

import com.fredodev.riee.treatment.application.dto.DentistTreatmentHistoryRequest;
import com.fredodev.riee.treatment.application.dto.DentistTreatmentHistoryResponse;
import com.fredodev.riee.treatment.application.usecases.DentistAssignmentUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DentistTreatmentService {

    private final DentistAssignmentUseCase assignmentUseCase;

    public DentistTreatmentHistoryResponse assignDentist(DentistTreatmentHistoryRequest request) {
        return assignmentUseCase.assignDentist(request);
    }

    public List<DentistTreatmentHistoryResponse> getAssignedDentists(Long treatmentHistoryId) {
        return assignmentUseCase.getAssignedDentists(treatmentHistoryId);
    }

    public DentistTreatmentHistoryResponse getPrincipalDentist(Long treatmentHistoryId) {
        return assignmentUseCase.getPrincipalDentist(treatmentHistoryId);
    }

    public void removeAssignment(Long assignmentId) {
        assignmentUseCase.removeAssignment(assignmentId);
    }
}
