package com.fredodev.riee.treatment.application.usecases;

import com.fredodev.riee.treatment.application.dto.DentalPieceResponse;
import com.fredodev.riee.treatment.application.dto.TreatmentHistoryRequest;
import com.fredodev.riee.treatment.application.dto.TreatmentHistoryResponse;
import com.fredodev.riee.treatment.domain.entity.DentalPieceEntity;
import com.fredodev.riee.treatment.domain.entity.TreatmentEntity;
import com.fredodev.riee.treatment.domain.entity.TreatmentHistoryEntity;
import com.fredodev.riee.treatment.domain.entity.TreatmentHistoryStatusEntity;
import com.fredodev.riee.treatment.domain.exception.TreatmentNotFoundException;
import com.fredodev.riee.treatment.domain.repository.DentalPieceRepository;
import com.fredodev.riee.treatment.domain.service.TreatmentDomainService;
import com.fredodev.riee.treatment.domain.service.TreatmentHistoryDomainService;
import com.fredodev.riee.treatment.domain.service.TreatmentHistoryStatusDomainService;
import com.fredodev.riee.patient.domain.entity.PatientEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TreatmentHistoryUseCase {

    private final TreatmentHistoryDomainService treatmentHistoryDomainService;
    private final TreatmentDomainService treatmentDomainService;
    private final TreatmentHistoryStatusDomainService statusService;
    private final DentalPieceRepository dentalPieceRepository;

    @Transactional
    public TreatmentHistoryResponse createTreatmentHistory(TreatmentHistoryRequest request) {
        TreatmentEntity treatment = treatmentDomainService.getTreatmentById(request.getTreatmentId())
                .orElseThrow(() -> new TreatmentNotFoundException(request.getTreatmentId()));

        TreatmentHistoryStatusEntity estadoPendiente = statusService.findByNombreEstado("PENDIENTE")
                .orElseGet(() -> statusService.saveStatus(
                        TreatmentHistoryStatusEntity.builder()
                                .nombreEstado("PENDIENTE")
                                .descripcion("Tratamiento pendiente de inicio")
                                .codigoColor("#FFA500")
                                .build()
                ));

        PatientEntity patient = new PatientEntity();
        patient.setId(request.getPatientId());

        List<DentalPieceEntity> dentalPieces = new ArrayList<>();
        if (request.getDentalPieceIds() != null && !request.getDentalPieceIds().isEmpty()) {
            dentalPieces = dentalPieceRepository.findByIdIn(request.getDentalPieceIds());
        }

        TreatmentHistoryEntity entity = TreatmentHistoryEntity.builder()
                .patient(patient)
                .treatment(treatment)
                .fechaInicioTratamiento(request.getFechaInicioTratamiento())
                .fechaFinTratamiento(request.getFechaFinTratamiento())
                .notasAdicionalesTratamientoPaciente(request.getNotasAdicionalesTratamientoPaciente())
                .precioTotalTratamiento(request.getPrecioCosto())
                .costoTotalTratamiento(request.getCostoTotalTratamiento())
                .descuentoTotalTratamiento(request.getDescuentoTotalTratamiento() != null ? request.getDescuentoTotalTratamiento() : 0.0)
                .saldoTotalTratamiento(request.getPrecioCosto())
                .estadoTratamiento(estadoPendiente)
                .dentalPieces(dentalPieces)
                .build();

        TreatmentHistoryEntity saved = treatmentHistoryDomainService.saveTreatmentHistory(entity);
        return mapToResponse(saved);
    }

    @Transactional(readOnly = true)
    public TreatmentHistoryResponse getTreatmentHistory(Long id) {
        TreatmentHistoryEntity entity = treatmentHistoryDomainService.getOrThrow(id);
        return mapToResponse(entity);
    }

    @Transactional(readOnly = true)
    public List<TreatmentHistoryResponse> getTreatmentHistoriesByPatient(Long patientId) {
        return treatmentHistoryDomainService.getTreatmentHistoriesByPatientId(patientId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public TreatmentHistoryResponse updateTreatmentHistory(Long id, TreatmentHistoryRequest request) {
        TreatmentHistoryEntity entity = treatmentHistoryDomainService.getOrThrow(id);

        entity.setFechaInicioTratamiento(request.getFechaInicioTratamiento());
        entity.setFechaFinTratamiento(request.getFechaFinTratamiento());
        entity.setNotasAdicionalesTratamientoPaciente(request.getNotasAdicionalesTratamientoPaciente());
        entity.setPrecioTotalTratamiento(request.getPrecioCosto());
        entity.setCostoTotalTratamiento(request.getCostoTotalTratamiento());
        if (request.getDescuentoTotalTratamiento() != null) {
            entity.setDescuentoTotalTratamiento(request.getDescuentoTotalTratamiento());
        }

        if (request.getDentalPieceIds() != null) {
            List<DentalPieceEntity> dentalPieces = dentalPieceRepository.findByIdIn(request.getDentalPieceIds());
            entity.setDentalPieces(dentalPieces);
        }

        TreatmentHistoryEntity saved = treatmentHistoryDomainService.saveTreatmentHistory(entity);
        return mapToResponse(saved);
    }

    @Transactional
    public TreatmentHistoryResponse updateStatus(Long treatmentHistoryId, Long newStatusId) {
        TreatmentHistoryEntity entity = treatmentHistoryDomainService.getOrThrow(treatmentHistoryId);
        TreatmentHistoryStatusEntity status = statusService.getByIdOrThrow(newStatusId);
        entity.setEstadoTratamiento(status);
        TreatmentHistoryEntity saved = treatmentHistoryDomainService.saveTreatmentHistory(entity);
        return mapToResponse(saved);
    }

    @Transactional
    public void deleteTreatmentHistory(Long id) {
        treatmentHistoryDomainService.deleteTreatmentHistory(id);
    }

    private TreatmentHistoryResponse mapToResponse(TreatmentHistoryEntity entity) {
        List<DentalPieceResponse> dentalPieceResponses = new ArrayList<>();
        if (entity.getDentalPieces() != null) {
            dentalPieceResponses = entity.getDentalPieces().stream()
                    .map(dp -> DentalPieceResponse.builder()
                            .id(dp.getId())
                            .numero(dp.getNumero())
                            .nombre(dp.getNombre())
                            .cuadrante(dp.getCuadrante())
                            .build())
                    .collect(Collectors.toList());
        }

        String patientNombre = null;
        Long patientId = null;
        if (entity.getPatient() != null) {
            patientId = entity.getPatient().getId();
            patientNombre = entity.getPatient().getNombre() + " " + entity.getPatient().getApellido();
        }

        String nombreTratamiento = null;
        Long treatmentId = null;
        if (entity.getTreatment() != null) {
            treatmentId = entity.getTreatment().getId();
            nombreTratamiento = entity.getTreatment().getNombreTratamiento();
        }

        Long estadoId = null;
        String estadoNombre = null;
        if (entity.getEstadoTratamiento() != null) {
            estadoId = entity.getEstadoTratamiento().getId();
            estadoNombre = entity.getEstadoTratamiento().getNombreEstado();
        }

        return TreatmentHistoryResponse.builder()
                .id(entity.getId())
                .patientId(patientId)
                .patientNombre(patientNombre)
                .treatmentId(treatmentId)
                .nombreTratamiento(nombreTratamiento)
                .fechaInicioTratamiento(entity.getFechaInicioTratamiento())
                .fechaFinTratamiento(entity.getFechaFinTratamiento())
                .notasAdicionalesTratamientoPaciente(entity.getNotasAdicionalesTratamientoPaciente())
                .precioTotalTratamiento(entity.getPrecioTotalTratamiento())
                .costoTotalTratamiento(entity.getCostoTotalTratamiento())
                .descuentoTotalTratamiento(entity.getDescuentoTotalTratamiento())
                .saldoTotalTratamiento(entity.getSaldoTotalTratamiento())
                .estadoId(estadoId)
                .estadoNombre(estadoNombre)
                .dentalPieces(dentalPieceResponses)
                .build();
    }
}
