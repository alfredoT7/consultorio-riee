package com.fredodev.riee.treatment.application.usecases;

import com.fredodev.riee.treatment.application.dto.TreatmentRequest;
import com.fredodev.riee.treatment.application.dto.TreatmentResponse;
import com.fredodev.riee.treatment.domain.entity.TreatmentEntity;
import com.fredodev.riee.treatment.domain.exception.DuplicateTreatmentException;
import com.fredodev.riee.treatment.domain.exception.TreatmentNotFoundException;
import com.fredodev.riee.treatment.domain.service.TreatmentDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TreatmentUseCase {
    private final TreatmentDomainService treatmentDomainService;

    @Transactional
    public TreatmentResponse createTreatment(TreatmentRequest request) {
        if (treatmentDomainService.existsByNombreTratamiento(request.getNombreTratamiento())) {
            throw new DuplicateTreatmentException(request.getNombreTratamiento());
        }

        TreatmentEntity treatmentEntity = mapToEntity(request);
        TreatmentEntity savedTreatment = treatmentDomainService.saveTreatment(treatmentEntity);
        return mapToResponse(savedTreatment);
    }

    @Transactional(readOnly = true)
    public TreatmentResponse getTreatmentById(Long id) {
        TreatmentEntity treatment = treatmentDomainService.getTreatmentById(id)
                .orElseThrow(() -> new TreatmentNotFoundException(id));
        return mapToResponse(treatment);
    }

    @Transactional(readOnly = true)
    public List<TreatmentResponse> getAllTreatments() {
        return treatmentDomainService.getAllTreatments().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public TreatmentResponse updateTreatment(Long id, TreatmentRequest request) {
        TreatmentEntity existingTreatment = treatmentDomainService.getTreatmentById(id)
                .orElseThrow(() -> new TreatmentNotFoundException(id));
        if (!existingTreatment.getNombreTratamiento().equals(request.getNombreTratamiento()) &&
                treatmentDomainService.existsByNombreTratamiento(request.getNombreTratamiento())) {
            throw new DuplicateTreatmentException(request.getNombreTratamiento());
        }
        existingTreatment.setNombreTratamiento(request.getNombreTratamiento());
        existingTreatment.setDescripcion(request.getDescripcion());
        existingTreatment.setProcedimiento(request.getProcedimiento());
        existingTreatment.setSemanasEstimadas(request.getSemanasEstimadas());
        existingTreatment.setCostoBaseTratamiento(request.getCostoBaseTratamiento());
        existingTreatment.setNotasAdicionales(request.getNotasAdicionales());

        TreatmentEntity updatedTreatment = treatmentDomainService.saveTreatment(existingTreatment);
        return mapToResponse(updatedTreatment);
    }

    @Transactional
    public void deleteTreatment(Long id) {
        if (!treatmentDomainService.getTreatmentById(id).isPresent()) {
            throw new TreatmentNotFoundException(id);
        }
        treatmentDomainService.deleteTreatment(id);
    }
    private TreatmentEntity mapToEntity(TreatmentRequest request) {
        return TreatmentEntity.builder()
                .nombreTratamiento(request.getNombreTratamiento())
                .descripcion(request.getDescripcion())
                .procedimiento(request.getProcedimiento())
                .semanasEstimadas(request.getSemanasEstimadas())
                .costoBaseTratamiento(request.getCostoBaseTratamiento())
                .notasAdicionales(request.getNotasAdicionales())
                .build();
    }

    private TreatmentResponse mapToResponse(TreatmentEntity entity) {
        return TreatmentResponse.builder()
                .id(entity.getId())
                .nombreTratamiento(entity.getNombreTratamiento())
                .descripcion(entity.getDescripcion())
                .procedimiento(entity.getProcedimiento())
                .semanasEstimadas(entity.getSemanasEstimadas())
                .costoBaseTratamiento(entity.getCostoBaseTratamiento())
                .notasAdicionales(entity.getNotasAdicionales())
                .build();
    }
}
