package com.fredodev.riee.treatment.infrastructure.rest;

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
    public ResponseEntity<TreatmentResponse> createTreatment(@Valid @RequestBody TreatmentRequest request) {
        TreatmentResponse createdTreatment = treatmentService.createTreatment(request);
        return new ResponseEntity<>(createdTreatment, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TreatmentResponse> getTreatmentById(@PathVariable Long id) {
        TreatmentResponse treatment = treatmentService.getTreatmentById(id);
        return ResponseEntity.ok(treatment);
    }

    @GetMapping
    public ResponseEntity<List<TreatmentResponse>> getAllTreatments() {
        List<TreatmentResponse> treatments = treatmentService.getAllTreatments();
        return ResponseEntity.ok(treatments);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TreatmentResponse> updateTreatment(
            @PathVariable Long id,
            @Valid @RequestBody TreatmentRequest request) {
        TreatmentResponse updatedTreatment = treatmentService.updateTreatment(id, request);
        return ResponseEntity.ok(updatedTreatment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTreatment(@PathVariable Long id) {
        treatmentService.deleteTreatment(id);
        return ResponseEntity.noContent().build();
    }
}
