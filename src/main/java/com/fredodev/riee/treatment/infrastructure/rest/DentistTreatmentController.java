package com.fredodev.riee.treatment.infrastructure.rest;

import com.fredodev.riee.shared.api.ApiResponse;
import com.fredodev.riee.treatment.application.dto.DentistTreatmentHistoryRequest;
import com.fredodev.riee.treatment.application.dto.DentistTreatmentHistoryResponse;
import com.fredodev.riee.treatment.application.service.DentistTreatmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/treatment-histories/{treatmentHistoryId}/dentists")
@RequiredArgsConstructor
public class DentistTreatmentController {

    private final DentistTreatmentService dentistTreatmentService;

    @PostMapping
    public ResponseEntity<ApiResponse<DentistTreatmentHistoryResponse>> assignDentist(
            @PathVariable Long treatmentHistoryId,
            @Valid @RequestBody DentistTreatmentHistoryRequest request) {
        request.setTreatmentHistoryId(treatmentHistoryId);
        DentistTreatmentHistoryResponse response = dentistTreatmentService.assignDentist(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok(201, "Dentista asignado exitosamente", response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<DentistTreatmentHistoryResponse>>> getDentistsAssigned(
            @PathVariable Long treatmentHistoryId) {
        List<DentistTreatmentHistoryResponse> response = dentistTreatmentService.getAssignedDentists(treatmentHistoryId);
        return ResponseEntity.ok(ApiResponse.ok(200, "Dentistas asignados", response));
    }

    @GetMapping("/principal")
    public ResponseEntity<ApiResponse<DentistTreatmentHistoryResponse>> getPrincipalDentist(
            @PathVariable Long treatmentHistoryId) {
        DentistTreatmentHistoryResponse response = dentistTreatmentService.getPrincipalDentist(treatmentHistoryId);
        return ResponseEntity.ok(ApiResponse.ok(200, "Dentista principal", response));
    }

    @DeleteMapping("/{assignmentId}")
    public ResponseEntity<ApiResponse<Void>> removeAssignment(
            @PathVariable Long treatmentHistoryId,
            @PathVariable Long assignmentId) {
        dentistTreatmentService.removeAssignment(assignmentId);
        return ResponseEntity.ok(ApiResponse.ok(200, "Asignación removida exitosamente", null));
    }
}
