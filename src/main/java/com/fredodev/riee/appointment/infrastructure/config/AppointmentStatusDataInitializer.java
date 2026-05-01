package com.fredodev.riee.appointment.infrastructure.config;

import com.fredodev.riee.appointment.domain.entity.AppointmentStatusEntity;
import com.fredodev.riee.appointment.domain.repository.AppointmentStatusRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AppointmentStatusDataInitializer implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(AppointmentStatusDataInitializer.class);

    private static final List<String> DEFAULT_STATUSES = List.of(
            "PROGRAMADA",
            "CONFIRMADA",
            "COMPLETADA",
            "CANCELADA",
            "NO_ASISTIO"
    );

    private final AppointmentStatusRepository appointmentStatusRepository;

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        List<AppointmentStatusEntity> existingEntities = appointmentStatusRepository.findByStatusIn(DEFAULT_STATUSES);

        Set<String> existingStatuses = existingEntities.stream()
                .map(AppointmentStatusEntity::getStatus)
                .collect(Collectors.toCollection(LinkedHashSet::new));

        existingEntities.forEach(entity ->
                log.info("Appointment status verified: status='{}', id={}", entity.getStatus(), entity.getId())
        );

        List<AppointmentStatusEntity> missingStatuses = DEFAULT_STATUSES.stream()
                .filter(status -> !existingStatuses.contains(status))
                .map(status -> AppointmentStatusEntity.builder().status(status).build())
                .toList();

        if (!missingStatuses.isEmpty()) {
            List<AppointmentStatusEntity> savedStatuses = appointmentStatusRepository.saveAll(missingStatuses);
            savedStatuses.forEach(entity ->
                    log.info("Appointment status created: status='{}', id={}", entity.getStatus(), entity.getId())
            );
        }
    }
}
