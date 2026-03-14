package com.fredodev.riee.dentist.infrastructure.rest;

import com.fredodev.riee.dentist.application.dto.DentistRequest;
import com.fredodev.riee.dentist.application.dto.DentistResponse;
import com.fredodev.riee.dentist.application.dto.DentistUpdateMultipartRequest;
import com.fredodev.riee.dentist.application.service.DentistService;
import com.fredodev.riee.shared.api.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dentists")
@RequiredArgsConstructor
public class DentistController {
    private final DentistService dentistService;

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<DentistResponse>> updateDentist(@PathVariable Long id, @RequestBody DentistRequest request) {
        DentistResponse response = dentistService.updateDentist(id, request);
        return ResponseEntity.ok(ApiResponse.ok(HttpStatus.OK.value(), "Dentist updated successfully", response));
    }

    @PutMapping(value = "/{id}/edit", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<DentistResponse>> updateDentistWithImage(
            @PathVariable Long id,
            @Valid @ModelAttribute DentistUpdateMultipartRequest request
    ) {
        DentistResponse response = dentistService.updateDentistWithImage(id, request);
        return ResponseEntity.ok(ApiResponse.ok(HttpStatus.OK.value(), "Dentist updated successfully", response));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<DentistResponse>> getDentistById(@PathVariable Long id) {
        DentistResponse response = dentistService.getDentistById(id);
        return ResponseEntity.ok(ApiResponse.ok(HttpStatus.OK.value(), "Dentist found", response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<DentistResponse>>> getAllDentists() {
        List<DentistResponse> responses = dentistService.getAllDentists();
        return ResponseEntity.ok(ApiResponse.ok(HttpStatus.OK.value(), "Dentists found", responses));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteDentist(@PathVariable Long id) {
        dentistService.deleteDentist(id);
        return ResponseEntity.ok(ApiResponse.ok(HttpStatus.OK.value(), "Dentist deleted successfully", null));
    }
}
