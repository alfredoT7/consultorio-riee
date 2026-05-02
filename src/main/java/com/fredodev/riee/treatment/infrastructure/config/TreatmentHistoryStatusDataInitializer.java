package com.fredodev.riee.treatment.infrastructure.config;

import com.fredodev.riee.treatment.domain.entity.TreatmentHistoryStatusEntity;
import com.fredodev.riee.treatment.domain.repository.TreatmentHistoryStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TreatmentHistoryStatusDataInitializer implements ApplicationRunner {

    private final TreatmentHistoryStatusRepository statusRepository;

    private static final List<TreatmentHistoryStatusEntity> DEFAULT_STATUSES = List.of(
            TreatmentHistoryStatusEntity.builder().nombreEstado("PENDIENTE").descripcion("Tratamiento pendiente de inicio").codigoColor("#FFA500").build(),
            TreatmentHistoryStatusEntity.builder().nombreEstado("EN_PROCESO").descripcion("Tratamiento en progreso").codigoColor("#2196F3").build(),
            TreatmentHistoryStatusEntity.builder().nombreEstado("PAUSADO").descripcion("Tratamiento pausado temporalmente").codigoColor("#9E9E9E").build(),
            TreatmentHistoryStatusEntity.builder().nombreEstado("FINALIZADO").descripcion("Tratamiento completado").codigoColor("#4CAF50").build(),
            TreatmentHistoryStatusEntity.builder().nombreEstado("CANCELADO").descripcion("Tratamiento cancelado").codigoColor("#F44336").build()
    );

    @Override
    public void run(ApplicationArguments args) {
        for (TreatmentHistoryStatusEntity status : DEFAULT_STATUSES) {
            if (statusRepository.findByNombreEstado(status.getNombreEstado()).isEmpty()) {
                statusRepository.save(status);
            }
        }
    }
}
