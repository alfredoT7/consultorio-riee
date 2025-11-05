package com.fredodev.riee.dentist.application.usecases;

import com.fredodev.riee.dentist.application.dto.SpecialityRequest;
import com.fredodev.riee.dentist.application.dto.SpecialityResponse;
import com.fredodev.riee.dentist.domain.entity.SpecialityEntity;
import com.fredodev.riee.dentist.domain.exception.SpecialityNotFoundException;
import com.fredodev.riee.dentist.domain.service.SpecialityDomainService;
import com.fredodev.riee.dentist.domain.service.SpecialityDomainValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class SpecialityUseCase {
    private final SpecialityDomainService specialityDomainService;
    private final SpecialityDomainValidator specialityDomainValidator;

    @Transactional
    public SpecialityResponse registerSpeciality(SpecialityRequest request) {
        SpecialityEntity entity = mapToEntity(request);
        specialityDomainValidator.validateBeforeCreate(entity);
        SpecialityEntity saved = specialityDomainService.saveSpeciality(entity);
        return mapToResponse(saved);
    }

    @Transactional
    public SpecialityResponse updateSpeciality(Long id, SpecialityRequest request) {
        SpecialityEntity entity = mapToEntity(request);
        specialityDomainValidator.validateBeforeUpdate(id, entity);
        SpecialityEntity existing = specialityDomainService.getSpecialityById(id)
            .orElseThrow(() -> new SpecialityNotFoundException("Speciality not found"));
        updateEntity(existing, request);
        SpecialityEntity updated = specialityDomainService.saveSpeciality(existing);
        return mapToResponse(updated);
    }

    public SpecialityResponse getSpecialityById(Long id) {
        SpecialityEntity entity = specialityDomainService.getSpecialityById(id)
            .orElseThrow(() -> new SpecialityNotFoundException("Speciality not found"));
        return mapToResponse(entity);
    }

    public List<SpecialityResponse> getAllSpecialities() {
        return specialityDomainService.getAllSpecialities().stream()
            .map(this::mapToResponse)
            .collect(Collectors.toList());
    }

    @Transactional
    public void deleteSpeciality(Long id) {
        SpecialityEntity entity = specialityDomainService.getSpecialityById(id)
            .orElseThrow(() -> new SpecialityNotFoundException("Speciality not found"));
        specialityDomainService.deleteSpeciality(id);
    }

    private SpecialityEntity mapToEntity(SpecialityRequest request) {
        return SpecialityEntity.builder()
            .nombre(request.getNombre())
            .descripcion(request.getDescripcion())
            .build();
    }

    private void updateEntity(SpecialityEntity entity, SpecialityRequest request) {
        entity.setNombre(request.getNombre());
        entity.setDescripcion(request.getDescripcion());
    }

    private SpecialityResponse mapToResponse(SpecialityEntity entity) {
        return SpecialityResponse.builder()
            .id(entity.getId())
            .nombre(entity.getNombre())
            .descripcion(entity.getDescripcion())
            .build();
    }
}

