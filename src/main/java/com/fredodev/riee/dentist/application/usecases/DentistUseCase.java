package com.fredodev.riee.dentist.application.usecases;

import com.fredodev.riee.dentist.application.dto.DentistRequest;
import com.fredodev.riee.dentist.application.dto.DentistResponse;
import com.fredodev.riee.dentist.domain.entity.DentistEntity;
import com.fredodev.riee.dentist.domain.entity.DentistSpecialityEntity;
import com.fredodev.riee.dentist.domain.entity.SpecialityEntity;
import com.fredodev.riee.dentist.domain.exception.InvalidDentistException;
import com.fredodev.riee.dentist.domain.service.DentistDomainService;
import com.fredodev.riee.dentist.domain.service.DentistDomainValidator;
import com.fredodev.riee.dentist.domain.repository.SpecialityRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class DentistUseCase {
    private final DentistDomainService dentistDomainService;
    private final DentistDomainValidator dentistDomainValidator;
    private final SpecialityRepository specialityRepository;

    @Transactional
    public DentistResponse registerDentist(DentistRequest request){
        dentistDomainValidator.validateBeforeCreate(request);
        DentistEntity entity = mapToEntity(request);
        DentistEntity saved = dentistDomainService.saveDentist(entity);
        return mapToResponse(saved);
    }

    @Transactional
    public DentistResponse updateDentist(Long id, DentistRequest request) {
        dentistDomainValidator.validateBeforeUpdate(id, request);
        DentistEntity existing = dentistDomainService.getDentistById(id)
            .orElseThrow(() -> new InvalidDentistException("Dentist not found"));
        updateEntity(existing, request);
        DentistEntity updated = dentistDomainService.editDentistDetail(existing);
        return mapToResponse(updated);
    }

    public DentistResponse getDentistById(Long id) {
        DentistEntity entity = dentistDomainService.getDentistById(id)
            .orElseThrow(() -> new InvalidDentistException("Dentist not found"));
        return mapToResponse(entity);
    }

    public List<DentistResponse> getAllDentists() {
        return dentistDomainService.getAllDentist().stream()
            .map(this::mapToResponse)
            .collect(Collectors.toList());
    }

    @Transactional
    public void deleteDentist(Long id) {
        DentistEntity entity = dentistDomainService.getDentistById(id)
            .orElseThrow(() -> new InvalidDentistException("Dentist not found"));
        dentistDomainService.deleteDentist(id);
    }

    private DentistEntity mapToEntity(DentistRequest request) {
        DentistEntity entity = DentistEntity.builder()
            .nombres(request.getNombres())
            .apellidos(request.getApellidos())
            .email(request.getEmail())
            .username(request.getUsername())
            .telefono(request.getTelefono())
            .ciDentista(request.getCiDentista())
            .universidad(request.getUniversidad())
            .promocion(request.getPromocion())
            .imagenUrl(request.getImagenUrl())
            .password("default") // TODO: handle password properly
            .build();
        if (request.getEspecialidadIds() != null && !request.getEspecialidadIds().isEmpty()) {
            List<DentistSpecialityEntity> specialities = request.getEspecialidadIds().stream()
                .map(id -> {
                    SpecialityEntity speciality = specialityRepository.findById(id)
                        .orElseThrow(() -> new InvalidDentistException("Speciality not found: " + id));
                    return DentistSpecialityEntity.builder()
                        .dentist(entity)
                        .speciality(speciality)
                        .build();
                })
                .collect(Collectors.toList());
            entity.setSpecialities(specialities);
        }
        return entity;
    }

    private void updateEntity(DentistEntity entity, DentistRequest request) {
        entity.setNombres(request.getNombres());
        entity.setApellidos(request.getApellidos());
        entity.setEmail(request.getEmail());
        entity.setUsername(request.getUsername());
        entity.setTelefono(request.getTelefono());
        entity.setCiDentista(request.getCiDentista());
        entity.setUniversidad(request.getUniversidad());
        entity.setPromocion(request.getPromocion());
        entity.setImagenUrl(request.getImagenUrl());
        if (request.getEspecialidadIds() != null) {
            List<DentistSpecialityEntity> specialities = request.getEspecialidadIds().stream()
                .map(id -> {
                    SpecialityEntity speciality = specialityRepository.findById(id)
                        .orElseThrow(() -> new InvalidDentistException("Speciality not found: " + id));
                    return DentistSpecialityEntity.builder()
                        .dentist(entity)
                        .speciality(speciality)
                        .build();
                })
                .collect(Collectors.toList());
            entity.setSpecialities(specialities);
        }
    }

    private DentistResponse mapToResponse(DentistEntity entity) {
        List<String> especialidades = entity.getSpecialities() != null ?
            entity.getSpecialities().stream()
                .map(ds -> ds.getSpeciality().getNombre())
                .collect(Collectors.toList()) : null;
        return DentistResponse.builder()
            .id(entity.getId())
            .nombres(entity.getNombres())
            .apellidos(entity.getApellidos())
            .email(entity.getEmail())
            .username(entity.getUsername())
            .telefono(entity.getTelefono() != null ? entity.getTelefono().toString() : null)
            .ciDentista(entity.getCiDentista() != null ? entity.getCiDentista().toString() : null)
            .universidad(entity.getUniversidad())
            .promocion(entity.getPromocion() != null ? entity.getPromocion().toString() : null)
            .imagenUrl(entity.getImagenUrl())
            .especialidades(especialidades)
            .build();
    }
}
