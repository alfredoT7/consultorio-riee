package com.fredodev.riee.treatment.application.service;

import com.fredodev.riee.treatment.application.dto.TreatmentRequest;
import com.fredodev.riee.treatment.application.dto.TreatmentResponse;
import com.fredodev.riee.treatment.application.usecases.TreatmentUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TreatmentService {
    private final TreatmentUseCase treatmentUseCase;

    public TreatmentResponse createTreatment(TreatmentRequest request) {
        return treatmentUseCase.createTreatment(request);
    }

    public TreatmentResponse getTreatmentById(Long id) {
        return treatmentUseCase.getTreatmentById(id);
    }

    public List<TreatmentResponse> getAllTreatments() {
        return treatmentUseCase.getAllTreatments();
    }

    public TreatmentResponse updateTreatment(Long id, TreatmentRequest request) {
        return treatmentUseCase.updateTreatment(id, request);
    }

    public void deleteTreatment(Long id) {
        treatmentUseCase.deleteTreatment(id);
    }
}
