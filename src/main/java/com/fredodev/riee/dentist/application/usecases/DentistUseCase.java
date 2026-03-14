package com.fredodev.riee.dentist.application.usecases;

import com.fredodev.riee.dentist.application.dto.DentistRequest;
import com.fredodev.riee.dentist.application.dto.DentistResponse;
import com.fredodev.riee.dentist.application.dto.DentistUpdateMultipartRequest;
import com.fredodev.riee.cloudinary.application.dto.CloudinaryUploadResponse;
import com.fredodev.riee.cloudinary.application.service.CloudinaryService;
import com.fredodev.riee.dentist.domain.entity.DentistEntity;
import com.fredodev.riee.dentist.domain.entity.SpecialityEntity;
import com.fredodev.riee.dentist.domain.exception.InvalidDentistException;
import com.fredodev.riee.dentist.domain.service.DentistDomainService;
import com.fredodev.riee.dentist.domain.service.DentistDomainValidator;
import com.fredodev.riee.dentist.domain.repository.SpecialityRepository;
import com.fredodev.riee.dentist.infrastructure.ports.persistence.JpaDentistSpecialityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class DentistUseCase {
    private static final String DENTIST_IMAGE_FOLDER = "riee/dentists";

    private final DentistDomainService dentistDomainService;
    private final JpaDentistSpecialityRepository dentistSpecialityRepository;
    private final DentistDomainValidator dentistDomainValidator;
    private final SpecialityRepository specialityRepository;
    private final CloudinaryService cloudinaryService;

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

        updateBasicFields(existing, request);
        updateSpecialities(existing, request.getEspecialidadIds());

        DentistEntity updated = dentistDomainService.editDentistDetail(existing);
        return mapToResponse(updated);
    }

    @Transactional
    public DentistResponse updateDentistWithImage(Long id, DentistUpdateMultipartRequest request) {
        dentistDomainValidator.validateBeforeUpdate(id, request);
        validateImageUpdateRequest(request);

        DentistEntity existing = dentistDomainService.getDentistById(id)
                .orElseThrow(() -> new InvalidDentistException("Dentist not found"));

        String previousPublicId = resolvePreviousPublicId(existing);
        String previousImageUrl = existing.getImagenUrl();

        updateBasicFields(existing, request);
        updateSpecialities(existing, request.getEspecialidadIds());

        CloudinaryUploadResponse uploadedImage = null;
        boolean shouldDeletePreviousImage = false;

        if (Boolean.TRUE.equals(request.getEliminarImagen())) {
            existing.setImagenUrl(null);
            existing.setImagenPublicId(null);
            shouldDeletePreviousImage = previousPublicId != null;
        } else if (request.getImagen() != null && !request.getImagen().isEmpty()) {
            String nextPublicId = buildDentistImagePublicId(request.getCiDentista(), request.getNombres(), request.getApellidos());
            uploadedImage = cloudinaryService.uploadImage(request.getImagen(), DENTIST_IMAGE_FOLDER, nextPublicId);
            existing.setImagenUrl(uploadedImage.getUrl());
            existing.setImagenPublicId(uploadedImage.getPublicId());
            shouldDeletePreviousImage = previousPublicId != null && !previousPublicId.equals(uploadedImage.getPublicId());
        } else {
            existing.setImagenUrl(previousImageUrl);
            existing.setImagenPublicId(previousPublicId);
        }

        try {
            DentistEntity updated = dentistDomainService.editDentistDetail(existing);
            if (shouldDeletePreviousImage) {
                tryDeleteImage(previousPublicId);
            }
            return mapToResponse(updated);
        } catch (RuntimeException exception) {
            if (uploadedImage != null && uploadedImage.getPublicId() != null && !uploadedImage.getPublicId().equals(previousPublicId)) {
                tryDeleteImage(uploadedImage.getPublicId());
            }
            existing.setImagenUrl(previousImageUrl);
            existing.setImagenPublicId(previousPublicId);
            throw exception;
        }
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
        dentistDomainService.getDentistById(id)
                .orElseThrow(() -> new InvalidDentistException("Dentist not found"));
        dentistSpecialityRepository.deleteAllByDentistId(id);
        dentistDomainService.deleteDentist(id);
    }

    private void updateBasicFields(DentistEntity entity, DentistRequest request) {
        entity.setNombres(request.getNombres());
        entity.setApellidos(request.getApellidos());
        entity.setEmail(request.getEmail());
        entity.setUsername(request.getUsername());
        entity.setTelefono(request.getTelefono());
        entity.setCiDentista(request.getCiDentista());
        entity.setUniversidad(request.getUniversidad());
        entity.setPromocion(request.getPromocion());
        entity.setImagenUrl(request.getImagenUrl());
    }

    private void updateBasicFields(DentistEntity entity, DentistUpdateMultipartRequest request) {
        entity.setNombres(request.getNombres());
        entity.setApellidos(request.getApellidos());
        entity.setEmail(request.getEmail());
        entity.setUsername(request.getUsername());
        entity.setTelefono(request.getTelefono());
        entity.setCiDentista(request.getCiDentista());
        entity.setUniversidad(request.getUniversidad());
        entity.setPromocion(request.getPromocion());
    }

    private void updateSpecialities(DentistEntity entity, List<Long> especialidadIds) {
        if (especialidadIds != null) {
            // delete existing links in dentist_specialities table (if any)
            dentistSpecialityRepository.deleteAllByDentistId(entity.getId());

            if (!especialidadIds.isEmpty()) {
                List<SpecialityEntity> newSpecialities = new ArrayList<>();

                for (Long especialidadId : especialidadIds) {
                    SpecialityEntity speciality = specialityRepository.findById(especialidadId)
                            .orElseThrow(() -> new InvalidDentistException("Speciality not found: " + especialidadId));

                    newSpecialities.add(speciality);
                }

                // set the SpecialityEntity list on the DentistEntity (ManyToMany)
                entity.setSpecialities(newSpecialities);
            } else {
                entity.setSpecialities(new ArrayList<>());
            }
        }
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
            List<SpecialityEntity> specialities = new ArrayList<>();
            for (Long especialidadId : request.getEspecialidadIds()) {
                SpecialityEntity speciality = specialityRepository.findById(especialidadId)
                        .orElseThrow(() -> new InvalidDentistException("Speciality not found: " + especialidadId));
                specialities.add(speciality);
            }
            entity.setSpecialities(specialities);
        }

        return entity;
    }

    private DentistResponse mapToResponse(DentistEntity entity) {
        List<String> especialidades = entity.getSpecialities() != null && !entity.getSpecialities().isEmpty() ?
                entity.getSpecialities().stream()
                        .map(SpecialityEntity::getNombre)
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

    private void validateImageUpdateRequest(DentistUpdateMultipartRequest request) {
        if (Boolean.TRUE.equals(request.getEliminarImagen()) && request.getImagen() != null && !request.getImagen().isEmpty()) {
            throw new InvalidDentistException("No puedes eliminar y subir una imagen al mismo tiempo");
        }
    }

    private String resolvePreviousPublicId(DentistEntity entity) {
        if (entity.getImagenPublicId() != null && !entity.getImagenPublicId().isBlank()) {
            return entity.getImagenPublicId();
        }
        return cloudinaryService.extractPublicId(entity.getImagenUrl()).orElse(null);
    }

    private String buildDentistImagePublicId(Long ciDentista, String nombres, String apellidos) {
        return sanitizeForCloudinary(ciDentista) + "=" + sanitizeForCloudinary(nombres + "-" + apellidos);
    }

    private String sanitizeForCloudinary(Object value) {
        if (value == null) {
            return "sin-valor";
        }
        String sanitized = String.valueOf(value)
                .trim()
                .toLowerCase()
                .replaceAll("[^a-z0-9]+", "-")
                .replaceAll("(^-+|-+$)", "");
        return sanitized.isBlank() ? "sin-valor" : sanitized;
    }

    private void tryDeleteImage(String publicId) {
        try {
            cloudinaryService.deleteImage(publicId);
        } catch (RuntimeException ignored) {
        }
    }
}
