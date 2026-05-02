package com.fredodev.riee.treatment.application.service;

import com.fredodev.riee.treatment.application.dto.BalanceResponse;
import com.fredodev.riee.treatment.application.dto.TreatmentPaymentRequest;
import com.fredodev.riee.treatment.application.dto.TreatmentPaymentResponse;
import com.fredodev.riee.treatment.application.usecases.TreatmentPaymentUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TreatmentPaymentService {

    private final TreatmentPaymentUseCase paymentUseCase;

    public TreatmentPaymentResponse registerPayment(TreatmentPaymentRequest request) {
        return paymentUseCase.registerPayment(request);
    }

    public List<TreatmentPaymentResponse> getPaymentsByTreatmentHistory(Long treatmentHistoryId) {
        return paymentUseCase.getPaymentsByTreatmentHistory(treatmentHistoryId);
    }

    public BalanceResponse getBalance(Long treatmentHistoryId) {
        return paymentUseCase.getBalance(treatmentHistoryId);
    }
}
