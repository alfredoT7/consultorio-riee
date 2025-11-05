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
import com.fredodev.riee.dentist.infrastructure.ports.persistence.JpaDentistSpecialityRepository;
import java.util.ArrayList;
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
    private final JpaDentistSpecialityRepository dentistSpecialityRepository;

    @Transactional
    public DentistResponse registerDentist(DentistRequest request) {
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

        // Update basic fields
        existing.setNombres(request.getNombres());
        existing.setApellidos(request.getApellidos());
        existing.setEmail(request.getEmail());
        existing.setUsername(request.getUsername());
        existing.setTelefono(request.getTelefono());
        existing.setCiDentista(request.getCiDentista());
        existing.setUniversidad(request.getUniversidad());
        existing.setPromocion(request.getPromocion());
        existing.setImagenUrl(request.getImagenUrl());

        // Delete old specialities and create new ones
        if (request.getEspecialidadIds() != null) {
            dentistSpecialityRepository.deleteAllByDentistId(id);

            if (!request.getEspecialidadIds().isEmpty()) {
                List<DentistSpecialityEntity> newSpecialities = new ArrayList<>();
                for (Long especialidadId : request.getEspecialidadIds()) {
                    SpecialityEntity speciality = specialityRepository.findById(especialidadId)
                        .orElseThrow(() -> new InvalidDentistException("Speciality not found: " + especialidadId));
                    DentistSpecialityEntity dentistSpeciality = DentistSpecialityEntity.builder()
                        .dentist(existing)
                        .speciality(speciality)
                        .build();
                    newSpecialities.add(dentistSpeciality);
                }
                dentistSpecialityRepository.saveAll(newSpecialities);
                existing.setSpecialities(newSpecialities);
            } else {
                existing.setSpecialities(new ArrayList<>());
            }
        }

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
        // Verify dentist exists before deleting
        dentistDomainService.getDentistById(id)
            .orElseThrow(() -> new InvalidDentistException("Dentist not found"));
        // Delete specialities first (optional, cascade will handle it)
        dentistSpecialityRepository.deleteAllByDentistId(id);
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
            .password("default")
            .specialities(new ArrayList<>())
            .build();

        if (request.getEspecialidadIds() != null && !request.getEspecialidadIds().isEmpty()) {
            List<DentistSpecialityEntity> specialities = new ArrayList<>();
            for (Long especialidadId : request.getEspecialidadIds()) {
                SpecialityEntity speciality = specialityRepository.findById(especialidadId)
                    .orElseThrow(() -> new InvalidDentistException("Speciality not found: " + especialidadId));
                DentistSpecialityEntity dentistSpeciality = DentistSpecialityEntity.builder()
                    .dentist(entity)
                    .speciality(speciality)
                    .build();
                specialities.add(dentistSpeciality);
            }
            entity.setSpecialities(specialities);
        }

        return entity;
    }

    private DentistResponse mapToResponse(DentistEntity entity) {
        List<String> especialidades = entity.getSpecialities() != null && !entity.getSpecialities().isEmpty() ?
            entity.getSpecialities().stream()
                .map(ds -> ds.getSpeciality().getNombre())
                .collect(Collectors.toList()) : new ArrayList<>();

        return DentistResponse.builder()
            .id(entity.getId())
            .nombres(entity.getNombres())
            .apellidos(entity.getApellidos())
            .email(entity.getEmail())
            .username(entity.getUsername())
            .telefono(entity.getTelefono())
            .ciDentista(entity.getCiDentista())
            .universidad(entity.getUniversidad())
            .promocion(entity.getPromocion())
            .imagenUrl(entity.getImagenUrl())
            .especialidades(especialidades)
            .build();
    }
}



