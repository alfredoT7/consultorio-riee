package com.fredodev.riee.treatment.infrastructure.config;

import com.fredodev.riee.treatment.domain.entity.TreatmentHistoryStatusEntity;
import com.fredodev.riee.treatment.domain.repository.TreatmentHistoryStatusRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TreatmentHistoryStatusDataInitializer implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(TreatmentHistoryStatusDataInitializer.class);

    private static final Map<String, TreatmentHistoryStatusEntity> DEFAULT_STATUSES;

    static {
        DEFAULT_STATUSES = new LinkedHashMap<>();
        DEFAULT_STATUSES.put("PENDIENTE",    TreatmentHistoryStatusEntity.builder().nombreEstado("PENDIENTE").descripcion("Tratamiento pendiente de inicio").codigoColor("#FFA500").build());
        DEFAULT_STATUSES.put("ACTIVO",       TreatmentHistoryStatusEntity.builder().nombreEstado("ACTIVO").descripcion("Tratamiento activo").codigoColor("#00b09b").build());
        DEFAULT_STATUSES.put("EN_PROGRESO",  TreatmentHistoryStatusEntity.builder().nombreEstado("EN_PROGRESO").descripcion("Tratamiento en progreso").codigoColor("#2196F3").build());
        DEFAULT_STATUSES.put("PAUSADO",      TreatmentHistoryStatusEntity.builder().nombreEstado("PAUSADO").descripcion("Tratamiento pausado temporalmente").codigoColor("#9E9E9E").build());
        DEFAULT_STATUSES.put("COMPLETADO",   TreatmentHistoryStatusEntity.builder().nombreEstado("COMPLETADO").descripcion("Tratamiento completado exitosamente").codigoColor("#4CAF50").build());
        DEFAULT_STATUSES.put("CANCELADO",    TreatmentHistoryStatusEntity.builder().nombreEstado("CANCELADO").descripcion("Tratamiento cancelado").codigoColor("#F44336").build());
    }

    private final TreatmentHistoryStatusRepository statusRepository;

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        List<TreatmentHistoryStatusEntity> existing = statusRepository.findByNombreEstadoIn(DEFAULT_STATUSES.keySet());

        Set<String> existingNames = existing.stream()
                .map(TreatmentHistoryStatusEntity::getNombreEstado)
                .collect(Collectors.toSet());

        existing.forEach(s -> log.info("Treatment status verified: name='{}', id={}", s.getNombreEstado(), s.getId()));

        List<TreatmentHistoryStatusEntity> missing = DEFAULT_STATUSES.entrySet().stream()
                .filter(e -> !existingNames.contains(e.getKey()))
                .map(Map.Entry::getValue)
                .toList();

        if (!missing.isEmpty()) {
            List<TreatmentHistoryStatusEntity> saved = statusRepository.saveAll(missing);
            saved.forEach(s -> log.info("Treatment status created: name='{}', id={}", s.getNombreEstado(), s.getId()));
        }
    }
}
