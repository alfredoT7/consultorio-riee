package com.fredodev.riee.treatment.infrastructure.rest;

import com.fredodev.riee.shared.api.ApiResponse;
import com.fredodev.riee.treatment.application.dto.TreatmentRequest;
import com.fredodev.riee.treatment.application.dto.TreatmentResponse;
import com.fredodev.riee.treatment.application.service.TreatmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/treatments")
@RequiredArgsConstructor
public class TreatmentController {
    private final TreatmentService treatmentService;

    @PostMapping
    public ResponseEntity<ApiResponse<TreatmentResponse>> createTreatment(@Valid @RequestBody TreatmentRequest request) {
        TreatmentResponse createdTreatment = treatmentService.createTreatment(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok(201, "Tratamiento creado exitosamente", createdTreatment));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TreatmentResponse>> getTreatmentById(@PathVariable Long id) {
        TreatmentResponse treatment = treatmentService.getTreatmentById(id);
        return ResponseEntity.ok(ApiResponse.ok(200, "Tratamiento obtenido exitosamente", treatment));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<TreatmentResponse>>> getAllTreatments() {
        List<TreatmentResponse> treatments = treatmentService.getAllTreatments();
        return ResponseEntity.ok(ApiResponse.ok(200, "Tratamientos obtenidos exitosamente", treatments));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<TreatmentResponse>> updateTreatment(
            @PathVariable Long id,
            @Valid @RequestBody TreatmentRequest request) {
        TreatmentResponse updatedTreatment = treatmentService.updateTreatment(id, request);
        return ResponseEntity.ok(ApiResponse.ok(200, "Tratamiento actualizado exitosamente", updatedTreatment));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteTreatment(@PathVariable Long id) {
        treatmentService.deleteTreatment(id);
        return ResponseEntity.ok(ApiResponse.ok(200, "Tratamiento eliminado exitosamente", null));
    }
}
