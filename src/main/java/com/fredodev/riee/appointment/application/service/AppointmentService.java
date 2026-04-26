package com.fredodev.riee.appointment.application.service;

import com.fredodev.riee.appointment.application.dto.AppointmentCalendarResponse;
import com.fredodev.riee.appointment.application.dto.AppointmentFilterRequest;
import com.fredodev.riee.appointment.application.dto.AppointmentRequest;
import com.fredodev.riee.appointment.application.dto.AppointmentRescheduleRequest;
import com.fredodev.riee.appointment.application.dto.AppointmentResponse;
import com.fredodev.riee.appointment.application.dto.AppointmentStatusResponse;
import com.fredodev.riee.appointment.application.dto.AvailabilitySlotResponse;
import com.fredodev.riee.appointment.application.usecases.AppointmentUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentService {
    private final AppointmentUseCase appointmentUseCase;

    public AppointmentResponse createAppointment(AppointmentRequest request) {
        return appointmentUseCase.createAppointment(request);
    }

    public AppointmentResponse getAppointmentById(Long id) {
        return appointmentUseCase.getAppointmentById(id);
    }

    public List<AppointmentResponse> getAllAppointments() {
        return appointmentUseCase.getAllAppointments();
    }

    public AppointmentResponse updateAppointment(Long id, AppointmentRequest request) {
        return appointmentUseCase.updateAppointment(id, request);
    }

    public void deleteAppointment(Long id) {
        appointmentUseCase.deleteAppointment(id);
    }

    public List<AppointmentResponse> getAppointmentsByPatientCi(int ciPaciente) {
        return appointmentUseCase.getAppointmentsByPatientCi(ciPaciente);
    }

    public List<AppointmentResponse> searchAppointments(AppointmentFilterRequest filter) {
        return appointmentUseCase.searchAppointments(filter);
    }

    public List<AppointmentResponse> getAppointmentsByDate(Date fechaCita) {
        return appointmentUseCase.getAppointmentsByDate(fechaCita);
    }

    public List<AppointmentCalendarResponse> getCalendarAppointments(AppointmentFilterRequest filter) {
        return appointmentUseCase.getCalendarAppointments(filter);
    }

    public List<AppointmentStatusResponse> getAppointmentStatuses() {
        return appointmentUseCase.getAppointmentStatuses();
    }

    public AppointmentResponse confirmAppointment(Long id, String observacionesCita) {
        return appointmentUseCase.confirmAppointment(id, observacionesCita);
    }

    public AppointmentResponse checkInAppointment(Long id, String observacionesCita) {
        return appointmentUseCase.checkInAppointment(id, observacionesCita);
    }

    public AppointmentResponse startAppointment(Long id, String observacionesCita) {
        return appointmentUseCase.startAppointment(id, observacionesCita);
    }

    public AppointmentResponse completeAppointment(Long id, String observacionesCita) {
        return appointmentUseCase.completeAppointment(id, observacionesCita);
    }

    public AppointmentResponse cancelAppointment(Long id, String observacionesCita) {
        return appointmentUseCase.cancelAppointment(id, observacionesCita);
    }

    public AppointmentResponse noShowAppointment(Long id, String observacionesCita) {
        return appointmentUseCase.noShowAppointment(id, observacionesCita);
    }

    public AppointmentResponse rescheduleAppointment(Long id, AppointmentRescheduleRequest request) {
        return appointmentUseCase.rescheduleAppointment(id, request);
    }

    public AppointmentResponse updateAppointmentStatus(Long id, Long appointmentStatusId, String observacionesCita) {
        return appointmentUseCase.updateAppointmentStatus(id, appointmentStatusId, observacionesCita);
    }

    public List<AvailabilitySlotResponse> getAvailableSlots(Date fechaCita, Integer durationMinutes, Integer slotMinutes) {
        return appointmentUseCase.getAvailableSlots(fechaCita, durationMinutes, slotMinutes);
    }

    public boolean hasTimeConflict(Date fechaCita, Time horaCita, Integer durationMinutes, Long excludeAppointmentId) {
        return appointmentUseCase.hasTimeConflict(fechaCita, horaCita, durationMinutes, excludeAppointmentId);
    }
}