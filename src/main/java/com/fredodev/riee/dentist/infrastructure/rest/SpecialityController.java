package com.fredodev.riee.dentist.infrastructure.rest;

import com.fredodev.riee.dentist.application.dto.SpecialityRequest;
import com.fredodev.riee.dentist.application.dto.SpecialityResponse;
import com.fredodev.riee.dentist.application.service.SpecialityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/specialities")
@RequiredArgsConstructor
public class SpecialityController {
    private final SpecialityService specialityService;

    @PostMapping
    public ResponseEntity<SpecialityResponse> createSpeciality(@RequestBody SpecialityRequest request) {
        return ResponseEntity.ok(specialityService.registerSpeciality(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SpecialityResponse> updateSpeciality(@PathVariable Long id, @RequestBody SpecialityRequest request) {
        return ResponseEntity.ok(specialityService.updateSpeciality(id, request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SpecialityResponse> getSpecialityById(@PathVariable Long id) {
        return ResponseEntity.ok(specialityService.getSpecialityById(id));
    }

    @GetMapping
    public ResponseEntity<List<SpecialityResponse>> getAllSpecialities() {
        return ResponseEntity.ok(specialityService.getAllSpecialities());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSpeciality(@PathVariable Long id) {
        specialityService.deleteSpeciality(id);
        return ResponseEntity.noContent().build();
    }
}
