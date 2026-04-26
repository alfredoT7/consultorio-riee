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

import java.util.List;

@Component
@RequiredArgsConstructor
public class AppointmentStatusDataInitializer implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(AppointmentStatusDataInitializer.class);

    private static final List<String> DEFAULT_STATUSES = List.of(
            "PENDIENTE",
            "CONFIRMADA",
            "REPROGRAMADA",
            "EN_ESPERA",
            "EN_CURSO",
            "COMPLETADA",
            "CANCELADA",
            "NO_ASISTIO"
    );

    private final AppointmentStatusRepository appointmentStatusRepository;

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        for (String status : DEFAULT_STATUSES) {
            var existing = appointmentStatusRepository.findByStatus(status);
            if (existing.isPresent()) {
                var e = existing.get();
                log.info("Appointment status already exists: status='{}', id={}", e.getStatus(), e.getId());
            } else {
                AppointmentStatusEntity saved = appointmentStatusRepository.save(
                        AppointmentStatusEntity.builder().status(status).build()
                );
                log.info("Appointment status created: status='{}', id={}", saved.getStatus(), saved.getId());
            }
        }
    }
}
