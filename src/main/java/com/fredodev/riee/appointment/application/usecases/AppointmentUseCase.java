package com.fredodev.riee.appointment.application.usecases;

import com.fredodev.riee.appointment.application.dto.AppointmentCalendarResponse;
import com.fredodev.riee.appointment.application.dto.AppointmentFilterRequest;
import com.fredodev.riee.appointment.application.dto.AppointmentRequest;
import com.fredodev.riee.appointment.application.dto.AppointmentRescheduleRequest;
import com.fredodev.riee.appointment.application.dto.AppointmentResponse;
import com.fredodev.riee.appointment.application.dto.AppointmentStatusResponse;
import com.fredodev.riee.appointment.application.dto.AvailabilitySlotResponse;
import com.fredodev.riee.appointment.domain.entity.AppointmentEntity;
import com.fredodev.riee.appointment.domain.entity.AppointmentStatusEntity;
import com.fredodev.riee.appointment.domain.exception.AppointmentNotFoundException;
import com.fredodev.riee.appointment.domain.exception.DuplicateAppointmentException;
import com.fredodev.riee.appointment.domain.exception.InvalidAppointmentException;
import com.fredodev.riee.appointment.domain.repository.AppointmentStatusRepository;
import com.fredodev.riee.appointment.domain.service.AppointmentDomainService;
import com.fredodev.riee.patient.domain.entity.PatientEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AppointmentUseCase {
    private static final int DEFAULT_DURATION_MINUTES = 30;
    private static final int DEFAULT_SLOT_MINUTES = 30;
    private static final LocalTime START_HOUR = LocalTime.of(8, 0);
    private static final LocalTime END_HOUR = LocalTime.of(18, 0);

    private static final Map<String, Set<String>> ALLOWED_TRANSITIONS = Map.of(
            "PENDIENTE", Set.of("CONFIRMADA", "CANCELADA", "NO_ASISTIO", "REPROGRAMADA"),
            "CONFIRMADA", Set.of("EN_ESPERA", "EN_CURSO", "CANCELADA", "NO_ASISTIO", "REPROGRAMADA"),
            "EN_ESPERA", Set.of("EN_CURSO", "CANCELADA", "NO_ASISTIO"),
            "EN_CURSO", Set.of("COMPLETADA", "CANCELADA"),
            "REPROGRAMADA", Set.of("CONFIRMADA", "CANCELADA", "NO_ASISTIO"),
            "COMPLETADA", Set.of(),
            "CANCELADA", Set.of(),
            "NO_ASISTIO", Set.of()
    );

    private final AppointmentDomainService appointmentDomainService;
    private final AppointmentStatusRepository appointmentStatusRepository;

    @Transactional
    public AppointmentResponse createAppointment(AppointmentRequest request) {
        validateDateTime(request.getFechaCita(), request.getHoraCita());

        int durationMinutes = normalizeDuration(request.getDuracionEstimada());
        if (hasTimeConflict(request.getFechaCita(), request.getHoraCita(), durationMinutes, null)) {
            throw new DuplicateAppointmentException("Ya existe una cita en ese horario");
        }

        AppointmentEntity appointmentEntity = mapToEntity(request);
        AppointmentEntity savedAppointment = appointmentDomainService.save(appointmentEntity);
        return mapToResponse(savedAppointment);
    }

    @Transactional(readOnly = true)
    public AppointmentResponse getAppointmentById(Long id) {
        AppointmentEntity appointment = appointmentDomainService.findById(id);
        if (appointment == null) {
            throw new AppointmentNotFoundException("Appointment not found with id: " + id);
        }
        return mapToResponse(appointment);
    }

    @Transactional(readOnly = true)
    public List<AppointmentResponse> getAllAppointments() {
        return appointmentDomainService.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public AppointmentResponse updateAppointment(Long id, AppointmentRequest request) {
        AppointmentEntity existingAppointment = appointmentDomainService.findById(id);
        if (existingAppointment == null) {
            throw new AppointmentNotFoundException("Appointment not found with id: " + id);
        }

        validateDateTime(request.getFechaCita(), request.getHoraCita());

        int durationMinutes = normalizeDuration(request.getDuracionEstimada());
        if (hasTimeConflict(request.getFechaCita(), request.getHoraCita(), durationMinutes, id)) {
            throw new DuplicateAppointmentException("Ya existe una cita en ese horario");
        }

        updateEntityFromRequest(existingAppointment, request);
        AppointmentEntity updatedAppointment = appointmentDomainService.save(existingAppointment);
        return mapToResponse(updatedAppointment);
    }

    @Transactional
    public void deleteAppointment(Long id) {
        if (!appointmentDomainService.existsById(id)) {
            throw new AppointmentNotFoundException("Appointment not found with id: " + id);
        }
        appointmentDomainService.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<AppointmentResponse> getAppointmentsByPatientCi(int ciPaciente) {
        return appointmentDomainService.findByCiPaciente(ciPaciente).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<AppointmentResponse> searchAppointments(AppointmentFilterRequest filter) {
        return appointmentDomainService.findByFilters(
                        filter.getFromDate(),
                        filter.getToDate(),
                        filter.getPatientCi(),
                        filter.getPatientId(),
                        filter.getAppointmentStatusId()
                ).stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<AppointmentResponse> getAppointmentsByDate(Date fechaCita) {
        if (fechaCita == null) {
            throw new InvalidAppointmentException("La fecha es requerida");
        }
        return appointmentDomainService.findByFechaCita(fechaCita).stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<AppointmentCalendarResponse> getCalendarAppointments(AppointmentFilterRequest filter) {
        return appointmentDomainService.findByFilters(
                        filter.getFromDate(),
                        filter.getToDate(),
                        filter.getPatientCi(),
                        filter.getPatientId(),
                        filter.getAppointmentStatusId()
                ).stream()
                .map(this::mapToCalendarResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<AppointmentStatusResponse> getAppointmentStatuses() {
        return appointmentStatusRepository.findAll().stream()
                .map(status -> AppointmentStatusResponse.builder()
                        .id(status.getId())
                        .status(status.getStatus())
                        .build())
                .toList();
    }

    @Transactional
    public AppointmentResponse confirmAppointment(Long id, String observacionesCita) {
        return mapToResponse(changeStatusByName(id, "CONFIRMADA", observacionesCita));
    }

    @Transactional
    public AppointmentResponse checkInAppointment(Long id, String observacionesCita) {
        return mapToResponse(changeStatusByName(id, "EN_ESPERA", observacionesCita));
    }

    @Transactional
    public AppointmentResponse startAppointment(Long id, String observacionesCita) {
        return mapToResponse(changeStatusByName(id, "EN_CURSO", observacionesCita));
    }

    @Transactional
    public AppointmentResponse completeAppointment(Long id, String observacionesCita) {
        return mapToResponse(changeStatusByName(id, "COMPLETADA", observacionesCita));
    }

    @Transactional
    public AppointmentResponse cancelAppointment(Long id, String observacionesCita) {
        return mapToResponse(changeStatusByName(id, "CANCELADA", observacionesCita));
    }

    @Transactional
    public AppointmentResponse noShowAppointment(Long id, String observacionesCita) {
        return mapToResponse(changeStatusByName(id, "NO_ASISTIO", observacionesCita));
    }

    @Transactional
    public AppointmentResponse rescheduleAppointment(Long id, AppointmentRescheduleRequest request) {
        if (request == null || request.getFechaCita() == null || request.getHoraCita() == null) {
            throw new InvalidAppointmentException("Fecha y hora son requeridas para reprogramar");
        }

        validateDateTime(request.getFechaCita(), request.getHoraCita());

        AppointmentEntity appointment = appointmentDomainService.findById(id);
        int durationMinutes = normalizeDuration(appointment.getDuracionEstimada());
        if (hasTimeConflict(request.getFechaCita(), request.getHoraCita(), durationMinutes, id)) {
            throw new DuplicateAppointmentException("Ya existe una cita en ese horario");
        }

        appointment.setFechaCita(request.getFechaCita());
        appointment.setHoraCita(request.getHoraCita());
        appointment.setObservacionesCita(mergeObservaciones(appointment.getObservacionesCita(), request.getObservacionesCita()));

        validateTransition(currentStatusName(appointment), "REPROGRAMADA");
        applyStatus(appointment, findStatusByName("REPROGRAMADA"));

        AppointmentEntity saved = appointmentDomainService.save(appointment);
        return mapToResponse(saved);
    }

    @Transactional
    public AppointmentResponse updateAppointmentStatus(Long id, Long appointmentStatusId, String observacionesCita) {
        if (appointmentStatusId == null) {
            throw new InvalidAppointmentException("appointmentStatusId es requerido");
        }

        AppointmentEntity appointment = appointmentDomainService.findById(id);
        AppointmentStatusEntity status = appointmentStatusRepository.findById(appointmentStatusId)
                .orElseThrow(() -> new InvalidAppointmentException("Estado de cita no encontrado"));

        validateTransition(currentStatusName(appointment), status.getStatus());
        applyStatus(appointment, status);
        appointment.setObservacionesCita(mergeObservaciones(appointment.getObservacionesCita(), observacionesCita));

        return mapToResponse(appointmentDomainService.save(appointment));
    }

    @Transactional(readOnly = true)
    public List<AvailabilitySlotResponse> getAvailableSlots(Date fechaCita, Integer durationMinutes, Integer slotMinutes) {
        if (fechaCita == null) {
            throw new InvalidAppointmentException("La fecha es requerida");
        }

        int normalizedDuration = durationMinutes == null || durationMinutes <= 0 ? DEFAULT_DURATION_MINUTES : durationMinutes;
        int normalizedSlot = slotMinutes == null || slotMinutes <= 0 ? DEFAULT_SLOT_MINUTES : slotMinutes;

        List<AppointmentEntity> appointments = appointmentDomainService.findByFechaCita(fechaCita);

        LocalTime cursor = START_HOUR;
        List<AvailabilitySlotResponse> slots = new java.util.ArrayList<>();
        while (!cursor.plusMinutes(normalizedDuration).isAfter(END_HOUR)) {
            LocalTime slotEnd = cursor.plusMinutes(normalizedDuration);
            if (!hasTimeConflict(appointments, cursor, slotEnd, null)) {
                slots.add(AvailabilitySlotResponse.builder()
                        .startTime(cursor.toString())
                        .endTime(slotEnd.toString())
                        .build());
            }
            cursor = cursor.plusMinutes(normalizedSlot);
        }

        return slots;
    }

    @Transactional(readOnly = true)
    public boolean hasTimeConflict(Date fechaCita, Time horaCita, Integer durationMinutes, Long excludeAppointmentId) {
        if (fechaCita == null || horaCita == null) {
            throw new InvalidAppointmentException("Fecha y hora son requeridas para validar conflictos");
        }

        int normalizedDuration = durationMinutes == null || durationMinutes <= 0 ? DEFAULT_DURATION_MINUTES : durationMinutes;
        LocalTime start = horaCita.toLocalTime();
        LocalTime end = start.plusMinutes(normalizedDuration);

        List<AppointmentEntity> appointments = appointmentDomainService.findByFechaCita(fechaCita);
        return hasTimeConflict(appointments, start, end, excludeAppointmentId);
    }

    private AppointmentEntity changeStatusByName(Long id, String targetStatus, String observacionesCita) {
        AppointmentEntity appointment = appointmentDomainService.findById(id);
        validateTransition(currentStatusName(appointment), targetStatus);
        applyStatus(appointment, findStatusByName(targetStatus));
        appointment.setObservacionesCita(mergeObservaciones(appointment.getObservacionesCita(), observacionesCita));
        return appointmentDomainService.save(appointment);
    }

    private void validateTransition(String currentStatus, String targetStatus) {
        if (targetStatus == null || targetStatus.isBlank()) {
            throw new InvalidAppointmentException("Estado de cita destino inválido");
        }
        if (currentStatus == null || currentStatus.isBlank()) {
            return;
        }
        if (currentStatus.equalsIgnoreCase(targetStatus)) {
            return;
        }

        Set<String> allowedTargets = ALLOWED_TRANSITIONS.getOrDefault(currentStatus.toUpperCase(), Set.of());
        if (!allowedTargets.contains(targetStatus.toUpperCase())) {
            throw new InvalidAppointmentException("No se puede cambiar de estado " + currentStatus + " a " + targetStatus);
        }
    }

    private String currentStatusName(AppointmentEntity appointment) {
        if (appointment.getAppointmentStatus() != null && appointment.getAppointmentStatus().getStatus() != null) {
            return appointment.getAppointmentStatus().getStatus();
        }
        return appointment.getEstadoCita();
    }

    private AppointmentStatusEntity findStatusByName(String statusName) {
        return appointmentStatusRepository.findByStatus(statusName)
                .orElseThrow(() -> new InvalidAppointmentException("No existe el estado de cita: " + statusName));
    }

    private void applyStatus(AppointmentEntity appointment, AppointmentStatusEntity status) {
        appointment.setAppointmentStatus(status);
        appointment.setEstadoCita(status.getStatus());
    }

    private String mergeObservaciones(String current, String incoming) {
        if (incoming == null || incoming.isBlank()) {
            return current;
        }
        if (current == null || current.isBlank()) {
            return incoming;
        }
        return current + " | " + incoming;
    }

    private void validateDateTime(Date fechaCita, Time horaCita) {
        Date today = new Date(System.currentTimeMillis());
        LocalTime now = LocalTime.now();

        if (fechaCita == null || horaCita == null) {
            throw new InvalidAppointmentException("Appointment date and time are required.");
        }
        if (fechaCita.before(today)) {
            throw new InvalidAppointmentException("Cannot schedule appointments in the past.");
        }
        if (fechaCita.equals(today) && horaCita.toLocalTime().isBefore(now)) {
            throw new InvalidAppointmentException("Cannot schedule appointments for past times today.");
        }

        LocalTime appointmentTime = horaCita.toLocalTime();
        if (appointmentTime.isBefore(START_HOUR) || appointmentTime.isAfter(END_HOUR)) {
            throw new InvalidAppointmentException("Appointments must be scheduled between 08:00 and 18:00.");
        }
    }

    private int normalizeDuration(Long duracionEstimada) {
        if (duracionEstimada == null || duracionEstimada <= 0) {
            return DEFAULT_DURATION_MINUTES;
        }
        return duracionEstimada.intValue();
    }

    private boolean hasTimeConflict(List<AppointmentEntity> appointments, LocalTime start, LocalTime end, Long excludeAppointmentId) {
        for (AppointmentEntity appointment : appointments) {
            if (excludeAppointmentId != null && excludeAppointmentId.equals(appointment.getId())) {
                continue;
            }
            if (appointment.getHoraCita() == null) {
                continue;
            }

            LocalTime appointmentStart = appointment.getHoraCita().toLocalTime();
            LocalTime appointmentEnd = appointmentStart.plusMinutes(normalizeDuration(appointment.getDuracionEstimada()));
            if (start.isBefore(appointmentEnd) && appointmentStart.isBefore(end)) {
                return true;
            }
        }
        return false;
    }

    private AppointmentEntity mapToEntity(AppointmentRequest request) {
        PatientEntity patient = new PatientEntity();
        patient.setId(request.getPatientId());

        AppointmentStatusEntity status = new AppointmentStatusEntity();
        status.setId(request.getAppointmentStatusId());

        return AppointmentEntity.builder()
                .fechaCita(request.getFechaCita())
                .horaCita(request.getHoraCita())
                .motivoCita(request.getMotivoCita())
                .estadoCita(request.getEstadoCita())
                .observacionesCita(request.getObservacionesCita())
                .duracionEstimada(request.getDuracionEstimada())
                .patient(patient)
                .appointmentStatus(status)
                .build();
    }

    private void updateEntityFromRequest(AppointmentEntity entity, AppointmentRequest request) {
        entity.setFechaCita(request.getFechaCita());
        entity.setHoraCita(request.getHoraCita());
        entity.setMotivoCita(request.getMotivoCita());
        entity.setEstadoCita(request.getEstadoCita());
        entity.setObservacionesCita(request.getObservacionesCita());
        entity.setDuracionEstimada(request.getDuracionEstimada());

        PatientEntity patient = new PatientEntity();
        patient.setId(request.getPatientId());
        entity.setPatient(patient);

        AppointmentStatusEntity status = new AppointmentStatusEntity();
        status.setId(request.getAppointmentStatusId());
        entity.setAppointmentStatus(status);
    }

    private AppointmentResponse mapToResponse(AppointmentEntity entity) {
        PatientEntity patient = entity.getPatient();

        return AppointmentResponse.builder()
                .id(entity.getId())
                .fechaCita(entity.getFechaCita())
                .horaCita(entity.getHoraCita())
                .motivoCita(entity.getMotivoCita())
                .estadoCita(entity.getEstadoCita())
                .observacionesCita(entity.getObservacionesCita())
                .duracionEstimada(entity.getDuracionEstimada())
                .patientId(patient.getId())
                .patientNombre(patient.getNombre())
                .patientApellido(patient.getApellido())
                .patientCi(patient.getCiPaciente())
                .patientEmail(patient.getEmail())
                .patientDireccion(patient.getDireccion())
                .appointmentStatusId(entity.getAppointmentStatus().getId())
                .appointmentStatusName(entity.getAppointmentStatus().getStatus())
                .build();
    }

    private AppointmentCalendarResponse mapToCalendarResponse(AppointmentEntity entity) {
        PatientEntity patient = entity.getPatient();
        String fullName = ((patient.getNombre() == null ? "" : patient.getNombre()) + " " +
                (patient.getApellido() == null ? "" : patient.getApellido())).trim();

        return AppointmentCalendarResponse.builder()
                .id(entity.getId())
                .fechaCita(entity.getFechaCita())
                .horaCita(entity.getHoraCita())
                .duracionEstimada(entity.getDuracionEstimada())
                .patientId(patient.getId())
                .patientNombreCompleto(fullName)
                .appointmentStatusId(entity.getAppointmentStatus().getId())
                .appointmentStatusName(entity.getAppointmentStatus().getStatus())
                .build();
    }
}

