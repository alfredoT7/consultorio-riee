package com.fredodev.riee.treatment.infrastructure.rest;

import com.fredodev.riee.shared.api.ApiResponse;
import com.fredodev.riee.treatment.application.dto.TreatmentHistoryRequest;
import com.fredodev.riee.treatment.application.dto.TreatmentHistoryResponse;
import com.fredodev.riee.treatment.application.service.TreatmentHistoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/treatment-histories")
@RequiredArgsConstructor
public class TreatmentHistoryController {

    private final TreatmentHistoryService treatmentHistoryService;

    @PostMapping
    public ResponseEntity<ApiResponse<TreatmentHistoryResponse>> createTreatmentHistory(
            @Valid @RequestBody TreatmentHistoryRequest request) {
        TreatmentHistoryResponse response = treatmentHistoryService.createTreatmentHistory(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok(201, "Historial de tratamiento creado exitosamente", response));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TreatmentHistoryResponse>> getTreatmentHistoryById(@PathVariable Long id) {
        TreatmentHistoryResponse response = treatmentHistoryService.getTreatmentHistoryById(id);
        return ResponseEntity.ok(ApiResponse.ok(200, "Historial de tratamiento obtenido", response));
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<ApiResponse<List<TreatmentHistoryResponse>>> getTreatmentHistoriesByPatient(
            @PathVariable Long patientId) {
        List<TreatmentHistoryResponse> response = treatmentHistoryService.getTreatmentHistoriesByPatient(patientId);
        return ResponseEntity.ok(ApiResponse.ok(200, "Historial de tratamientos del paciente", response));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<TreatmentHistoryResponse>> updateTreatmentHistory(
            @PathVariable Long id,
            @Valid @RequestBody TreatmentHistoryRequest request) {
        TreatmentHistoryResponse response = treatmentHistoryService.updateTreatmentHistory(id, request);
        return ResponseEntity.ok(ApiResponse.ok(200, "Historial actualizado exitosamente", response));
    }

    @PutMapping("/{id}/status/{statusId}")
    public ResponseEntity<ApiResponse<TreatmentHistoryResponse>> updateStatus(
            @PathVariable Long id,
            @PathVariable Long statusId) {
        TreatmentHistoryResponse response = treatmentHistoryService.updateStatusTreatmentHistory(id, statusId);
        return ResponseEntity.ok(ApiResponse.ok(200, "Estado del tratamiento actualizado", response));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteTreatmentHistory(@PathVariable Long id) {
        treatmentHistoryService.deleteTreatmentHistory(id);
        return ResponseEntity.ok(ApiResponse.ok(200, "Historial eliminado exitosamente", null));
    }
}
