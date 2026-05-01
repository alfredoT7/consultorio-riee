package com.fredodev.riee.appointment.application.usecases;

import com.fredodev.riee.appointment.application.dto.AppointmentPatientSummaryResponse;
import com.fredodev.riee.appointment.application.dto.AppointmentResponse;
import com.fredodev.riee.appointment.application.dto.AppointmentStatusResponse;
import com.fredodev.riee.appointment.application.dto.AppointmentStatusSummaryResponse;
import com.fredodev.riee.appointment.application.dto.AvailabilitySlotResponse;
import com.fredodev.riee.appointment.application.dto.CreateAppointmentRequest;
import com.fredodev.riee.appointment.application.dto.UpdateAppointmentRequest;
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
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.time.ZoneId;

@Component
@RequiredArgsConstructor
public class AppointmentUseCase {
    private static final int DEFAULT_DURATION_MINUTES = 30;
    private static final int SLOT_GRANULARITY_MINUTES = 15;
    private static final int DAY_START_MINUTES = 0;
    private static final int DAY_END_MINUTES = 24 * 60;
    private static final ZoneId APPOINTMENT_ZONE = ZoneId.of("America/La_Paz");
    private static final String DEFAULT_APPOINTMENT_STATUS = "PROGRAMADA";

    private static final Map<String, Set<String>> ALLOWED_TRANSITIONS = Map.of(
            "PROGRAMADA", Set.of("CONFIRMADA", "COMPLETADA", "CANCELADA", "NO_ASISTIO"),
            "CONFIRMADA", Set.of("COMPLETADA", "CANCELADA", "NO_ASISTIO"),
            "COMPLETADA", Set.of(),
            "CANCELADA", Set.of(),
            "NO_ASISTIO", Set.of()
    );

    private final AppointmentDomainService appointmentDomainService;
    private final AppointmentStatusRepository appointmentStatusRepository;

    @Transactional
    public AppointmentResponse createAppointment(CreateAppointmentRequest request) {
        validateCreateRequest(request);
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
    public List<AppointmentResponse> getAppointments(Date fromDate, Date toDate, Date exactDate, String statusName) {
        validateDateFilters(fromDate, toDate, exactDate);

        Date effectiveFrom = exactDate != null ? exactDate : fromDate;
        Date effectiveTo = exactDate != null ? exactDate : toDate;
        String normalizedStatus = normalizeStatusName(statusName);

        return appointmentDomainService.findByFilters(effectiveFrom, effectiveTo, normalizedStatus).stream()
                .map(this::mapToResponse)
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
    public AppointmentResponse updateAppointment(Long id, UpdateAppointmentRequest request) {
        AppointmentEntity existingAppointment = appointmentDomainService.findById(id);
        validateUpdateRequest(request);

        Date targetDate = request.getFechaCita() != null ? request.getFechaCita() : existingAppointment.getFechaCita();
        Time targetTime = request.getHoraCita() != null ? request.getHoraCita() : existingAppointment.getHoraCita();
        Long targetDuration = request.getDuracionEstimada() != null ? request.getDuracionEstimada() : existingAppointment.getDuracionEstimada();

        validateDateTime(targetDate, targetTime);

        int durationMinutes = normalizeDuration(targetDuration);
        if (hasTimeConflict(targetDate, targetTime, durationMinutes, id)) {
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
    public List<AvailabilitySlotResponse> getAvailableSlots(Date fechaCita, Integer durationMinutes) {
        if (fechaCita == null) {
            throw new InvalidAppointmentException("La fecha es requerida");
        }

        int normalizedDuration = durationMinutes == null || durationMinutes <= 0 ? DEFAULT_DURATION_MINUTES : durationMinutes;
        List<AppointmentEntity> appointments = appointmentDomainService.findByFechaCita(fechaCita);

        List<AvailabilitySlotResponse> slots = new java.util.ArrayList<>();
        for (int cursorMinutes = DAY_START_MINUTES; cursorMinutes + normalizedDuration < DAY_END_MINUTES; cursorMinutes += SLOT_GRANULARITY_MINUTES) {
            LocalTime cursor = toLocalTime(cursorMinutes);
            LocalTime slotEnd = toLocalTime(cursorMinutes + normalizedDuration);
            if (!hasTimeConflict(appointments, cursor, slotEnd, null)) {
                slots.add(AvailabilitySlotResponse.builder()
                        .startTime(cursor.toString())
                        .endTime(slotEnd.toString())
                        .build());
            }
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

    private AppointmentStatusEntity findStatusById(Long appointmentStatusId) {
        if (appointmentStatusId == null) {
            throw new InvalidAppointmentException("appointmentStatusId es requerido");
        }
        return appointmentStatusRepository.findById(appointmentStatusId)
                .orElseThrow(() -> new InvalidAppointmentException("Estado de cita no encontrado"));
    }

    private AppointmentStatusEntity resolveCreateStatus(Long appointmentStatusId) {
        if (appointmentStatusId == null) {
            return findStatusByName(DEFAULT_APPOINTMENT_STATUS);
        }
        return findStatusById(appointmentStatusId);
    }

    private void applyStatus(AppointmentEntity appointment, AppointmentStatusEntity status) {
        appointment.setAppointmentStatus(status);
        appointment.setEstadoCita(status.getStatus());
    }

    private void validateDateTime(Date fechaCita, Time horaCita) {
        LocalDate today = LocalDate.now(APPOINTMENT_ZONE);
        LocalTime now = LocalTime.now(APPOINTMENT_ZONE);
        LocalDate appointmentDate = fechaCita.toLocalDate();

        if (fechaCita == null || horaCita == null) {
            throw new InvalidAppointmentException("Appointment date and time are required.");
        }
        if (appointmentDate.isBefore(today)) {
            throw new InvalidAppointmentException("Cannot schedule appointments in the past.");
        }
        if (appointmentDate.equals(today) && horaCita.toLocalTime().isBefore(now)) {
            throw new InvalidAppointmentException("Cannot schedule appointments for past times today.");
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

    private LocalTime toLocalTime(int totalMinutes) {
        int hours = totalMinutes / 60;
        int minutes = totalMinutes % 60;
        return LocalTime.of(hours, minutes);
    }

    private AppointmentEntity mapToEntity(CreateAppointmentRequest request) {
        PatientEntity patient = new PatientEntity();
        patient.setId(request.getPatientId());

        AppointmentStatusEntity status = resolveCreateStatus(request.getAppointmentStatusId());

        return AppointmentEntity.builder()
                .fechaCita(request.getFechaCita())
                .horaCita(request.getHoraCita())
                .motivoCita(request.getMotivoCita())
                .estadoCita(status.getStatus())
                .observacionesCita(request.getObservacionesCita())
                .duracionEstimada(request.getDuracionEstimada())
                .patient(patient)
                .appointmentStatus(status)
                .build();
    }

    private void updateEntityFromRequest(AppointmentEntity entity, UpdateAppointmentRequest request) {
        if (request.getFechaCita() != null) {
            entity.setFechaCita(request.getFechaCita());
        }
        if (request.getHoraCita() != null) {
            entity.setHoraCita(request.getHoraCita());
        }
        if (request.getMotivoCita() != null) {
            entity.setMotivoCita(request.getMotivoCita());
        }
        if (request.getObservacionesCita() != null) {
            entity.setObservacionesCita(request.getObservacionesCita());
        }
        if (request.getDuracionEstimada() != null) {
            entity.setDuracionEstimada(request.getDuracionEstimada());
        }
        if (request.getPatientId() != null) {
            PatientEntity patient = new PatientEntity();
            patient.setId(request.getPatientId());
            entity.setPatient(patient);
        }
        if (request.getAppointmentStatusId() != null) {
            AppointmentStatusEntity status = findStatusById(request.getAppointmentStatusId());
            validateTransition(currentStatusName(entity), status.getStatus());
            applyStatus(entity, status);
        }
    }

    private AppointmentResponse mapToResponse(AppointmentEntity entity) {
        PatientEntity patient = entity.getPatient();

        return AppointmentResponse.builder()
                .id(entity.getId())
                .fechaCita(entity.getFechaCita())
                .horaCita(entity.getHoraCita())
                .motivoCita(entity.getMotivoCita())
                .observacionesCita(entity.getObservacionesCita())
                .duracionEstimada(entity.getDuracionEstimada())
                .patient(AppointmentPatientSummaryResponse.builder()
                        .id(patient.getId())
                        .nombre(patient.getNombre())
                        .apellido(patient.getApellido())
                        .ciPaciente(patient.getCiPaciente())
                        .email(patient.getEmail())
                        .direccion(patient.getDireccion())
                        .build())
                .status(AppointmentStatusSummaryResponse.builder()
                        .id(entity.getAppointmentStatus().getId())
                        .name(entity.getAppointmentStatus().getStatus())
                        .build())
                .build();
    }

    private void validateCreateRequest(CreateAppointmentRequest request) {
        if (request == null) {
            throw new InvalidAppointmentException("El body de la cita es requerido");
        }
        if (request.getPatientId() == null) {
            throw new InvalidAppointmentException("patientId es requerido");
        }
        if (request.getFechaCita() == null || request.getHoraCita() == null) {
            throw new InvalidAppointmentException("fechaCita y horaCita son requeridas");
        }
    }

    private void validateUpdateRequest(UpdateAppointmentRequest request) {
        if (request == null) {
            throw new InvalidAppointmentException("El body de actualizacion es requerido");
        }
        if (allFieldsNull(request)) {
            throw new InvalidAppointmentException("Debe enviar al menos un campo para actualizar");
        }
    }

    private boolean allFieldsNull(UpdateAppointmentRequest request) {
        return request.getPatientId() == null
                && request.getFechaCita() == null
                && request.getHoraCita() == null
                && request.getMotivoCita() == null
                && request.getDuracionEstimada() == null
                && request.getObservacionesCita() == null
                && request.getAppointmentStatusId() == null;
    }

    private void validateDateFilters(Date fromDate, Date toDate, Date exactDate) {
        if (exactDate != null && (fromDate != null || toDate != null)) {
            throw new InvalidAppointmentException("No puede combinar date con from/to");
        }
        if (fromDate != null && toDate != null && fromDate.after(toDate)) {
            throw new InvalidAppointmentException("El rango de fechas es invalido");
        }
    }

    private String normalizeStatusName(String statusName) {
        if (statusName == null || statusName.isBlank()) {
            return null;
        }
        String normalizedStatus = statusName.trim().toUpperCase(Locale.ROOT);
        findStatusByName(normalizedStatus);
        return normalizedStatus;
    }
}
