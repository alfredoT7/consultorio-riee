package com.fredodev.riee.appointment.application.usecases;

import com.fredodev.riee.appointment.application.dto.AppointmentRequest;
import com.fredodev.riee.appointment.application.dto.AppointmentResponse;
import com.fredodev.riee.appointment.domain.entity.AppointmentEntity;
import com.fredodev.riee.appointment.domain.entity.AppointmentStatusEntity;
import com.fredodev.riee.appointment.domain.exception.AppointmentNotFoundException;
import com.fredodev.riee.appointment.domain.service.AppointmentDomainService;
import com.fredodev.riee.patient.domain.entity.PatientEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AppointmentUseCase {
    private final AppointmentDomainService appointmentDomainService;
    //TODO: que se filte por tiempo a partir de la fecha actual por que no me sirven las anteirores
    @Transactional
    public AppointmentResponse createAppointment(AppointmentRequest request) {
        java.sql.Date today = new java.sql.Date(System.currentTimeMillis());
        java.time.LocalTime now = java.time.LocalTime.now();
        if (request.getFechaCita() == null || request.getHoraCita() == null) {
            throw new com.fredodev.riee.appointment.domain.exception.InvalidAppointmentException("Appointment date and time are required.");
        }
        if (request.getFechaCita().before(today)) {
            throw new com.fredodev.riee.appointment.domain.exception.InvalidAppointmentException("Cannot schedule appointments in the past.");
        }
        if (request.getFechaCita().equals(today) && request.getHoraCita().toLocalTime().isBefore(now)) {
            throw new com.fredodev.riee.appointment.domain.exception.InvalidAppointmentException("Cannot schedule appointments for past times today.");
        }
        java.time.LocalTime startHour = java.time.LocalTime.of(8, 0);
        java.time.LocalTime endHour = java.time.LocalTime.of(18, 0);
        java.time.LocalTime appointmentTime = request.getHoraCita().toLocalTime();
        if (appointmentTime.isBefore(startHour) || appointmentTime.isAfter(endHour)) {
            throw new com.fredodev.riee.appointment.domain.exception.InvalidAppointmentException("Appointments must be scheduled between 08:00 and 18:00.");
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
    @Transactional(readOnly = true)
    public List<AppointmentResponse> getAppointmentsByPatientCi(int ciPaciente) {
        return appointmentDomainService.findByCiPaciente(ciPaciente).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
}