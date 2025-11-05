package com.fredodev.riee.dentist.infrastructure.rest;

import com.fredodev.riee.dentist.application.dto.SpecialityRequest;
import com.fredodev.riee.dentist.application.dto.SpecialityResponse;
import com.fredodev.riee.dentist.application.service.SpecialityService;
import com.fredodev.riee.shared.api.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/specialities")
@RequiredArgsConstructor
public class SpecialityController {
    private final SpecialityService specialityService;

    @PostMapping
    public ResponseEntity<ApiResponse<SpecialityResponse>> createSpeciality(@RequestBody SpecialityRequest request) {
        SpecialityResponse response = specialityService.registerSpeciality(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok(HttpStatus.CREATED.value(), "Speciality created successfully", response));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<SpecialityResponse>> updateSpeciality(@PathVariable Long id, @RequestBody SpecialityRequest request) {
        SpecialityResponse response = specialityService.updateSpeciality(id, request);
        return ResponseEntity.ok(ApiResponse.ok(HttpStatus.OK.value(), "Speciality updated successfully", response));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<SpecialityResponse>> getSpecialityById(@PathVariable Long id) {
        SpecialityResponse response = specialityService.getSpecialityById(id);
        return ResponseEntity.ok(ApiResponse.ok(HttpStatus.OK.value(), "Speciality found", response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<SpecialityResponse>>> getAllSpecialities() {
        List<SpecialityResponse> responses = specialityService.getAllSpecialities();
        return ResponseEntity.ok(ApiResponse.ok(HttpStatus.OK.value(), "Specialities found", responses));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteSpeciality(@PathVariable Long id) {
        specialityService.deleteSpeciality(id);
        return ResponseEntity.ok(ApiResponse.ok(HttpStatus.OK.value(), "Speciality deleted successfully", null));
    }
}
