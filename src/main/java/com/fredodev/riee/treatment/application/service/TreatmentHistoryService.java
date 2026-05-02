package com.fredodev.riee.treatment.application.service;

import com.fredodev.riee.treatment.application.dto.TreatmentHistoryRequest;
import com.fredodev.riee.treatment.application.dto.TreatmentHistoryResponse;
import com.fredodev.riee.treatment.application.usecases.TreatmentHistoryUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TreatmentHistoryService {

    private final TreatmentHistoryUseCase treatmentHistoryUseCase;

    public TreatmentHistoryResponse createTreatmentHistory(TreatmentHistoryRequest request) {
        return treatmentHistoryUseCase.createTreatmentHistory(request);
    }

    public TreatmentHistoryResponse getTreatmentHistoryById(Long id) {
        return treatmentHistoryUseCase.getTreatmentHistory(id);
    }

    public List<TreatmentHistoryResponse> getTreatmentHistoriesByPatient(Long patientId) {
        return treatmentHistoryUseCase.getTreatmentHistoriesByPatient(patientId);
    }

    public TreatmentHistoryResponse updateTreatmentHistory(Long id, TreatmentHistoryRequest request) {
        return treatmentHistoryUseCase.updateTreatmentHistory(id, request);
    }

    public TreatmentHistoryResponse updateStatusTreatmentHistory(Long id, Long statusId) {
        return treatmentHistoryUseCase.updateStatus(id, statusId);
    }

    public void deleteTreatmentHistory(Long id) {
        treatmentHistoryUseCase.deleteTreatmentHistory(id);
    }
}
