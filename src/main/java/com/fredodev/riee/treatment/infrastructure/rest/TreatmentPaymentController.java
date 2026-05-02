package com.fredodev.riee.treatment.infrastructure.rest;

import com.fredodev.riee.shared.api.ApiResponse;
import com.fredodev.riee.treatment.application.dto.BalanceResponse;
import com.fredodev.riee.treatment.application.dto.TreatmentPaymentRequest;
import com.fredodev.riee.treatment.application.dto.TreatmentPaymentResponse;
import com.fredodev.riee.treatment.application.service.TreatmentPaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/treatment-histories/{treatmentHistoryId}/payments")
@RequiredArgsConstructor
public class TreatmentPaymentController {

    private final TreatmentPaymentService treatmentPaymentService;

    @PostMapping
    public ResponseEntity<ApiResponse<TreatmentPaymentResponse>> registerPayment(
            @PathVariable Long treatmentHistoryId,
            @Valid @RequestBody TreatmentPaymentRequest request) {
        request.setTreatmentHistoryId(treatmentHistoryId);
        TreatmentPaymentResponse response = treatmentPaymentService.registerPayment(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok(201, "Pago registrado exitosamente", response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<TreatmentPaymentResponse>>> getPayments(
            @PathVariable Long treatmentHistoryId) {
        List<TreatmentPaymentResponse> response = treatmentPaymentService.getPaymentsByTreatmentHistory(treatmentHistoryId);
        return ResponseEntity.ok(ApiResponse.ok(200, "Historial de pagos", response));
    }

    @GetMapping("/balance")
    public ResponseEntity<ApiResponse<BalanceResponse>> getBalance(@PathVariable Long treatmentHistoryId) {
        BalanceResponse response = treatmentPaymentService.getBalance(treatmentHistoryId);
        return ResponseEntity.ok(ApiResponse.ok(200, "Saldo de tratamiento", response));
    }
}
